package com.zjhc.sgsb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zjhc.sgsb.common.Constant;
import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.*;
import com.zjhc.sgsb.mapper.ResourceCatalogMapper;
import com.zjhc.sgsb.service.ICatalogTemplateService;
import com.zjhc.sgsb.service.IDeptService;
import com.zjhc.sgsb.service.IResourceCatalogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjhc.sgsb.service.IUploadRecordService;
import com.zjhc.sgsb.util.DBMSMetaUtil;
import com.zjhc.sgsb.util.ExcelUtil;
import com.zjhc.sgsb.util.YunHelper;
import net.sf.cglib.core.Local;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wusj
 * @since 2020-04-19
 */
@Service
@Transactional
public class ResourceCatalogServiceImpl extends ServiceImpl<ResourceCatalogMapper, ResourceCatalog> implements IResourceCatalogService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ResourceCatalogMapper resourceCatalogMapper;
    @Autowired
    private ResourceCatalogServiceImpl resourceCatalogService;
    @Autowired
    private ICatalogTemplateService catalogTemplateService;
    @Autowired
    private IUploadRecordService uploadRecordService;
    @Autowired
    private IDeptService deptService;

    public InterfaceResult<List<ResourceCatalog>> listResourceCatalog(UserInfo userInfo,ResourceCatalog catalog,Page<ResourceCatalog> page){
        QueryWrapper<ResourceCatalog> wrapper = new QueryWrapper<>();
        if (userInfo.getIsSuperUser() != 1){
            wrapper.eq("dept_code",userInfo.getDeptCode());
        }
        if (catalog != null && catalog.getCatalogName() != null){
            wrapper.like("catalog_name",catalog.getCatalogName());
        }
        wrapper.eq("is_delete",1);
        wrapper.orderByDesc("orderby","create_time");
        List<ResourceCatalog> resourceCatalogListist = resourceCatalogService.list(wrapper);
        IPage<ResourceCatalog> iPage = resourceCatalogMapper.selectPage(page,wrapper);
        iPage.setTotal(resourceCatalogListist.size());
        if (iPage.getTotal() > 0){
            return InterfaceResult.getSuccess(iPage.getTotal(), iPage.getPages(),iPage.getRecords());
        }
        return InterfaceResult.getError("结果为空！"+Lists.newArrayList());
    }

    public InterfaceResult<ResourceCatalog> addResourceCatalog(UserInfo userInfo,ResourceCatalog catalog,MultipartFile excel) {
        QueryWrapper<ResourceCatalog> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog_name",catalog.getCatalogName());
        wrapper.eq("is_delete",1);
        Integer hasExist = resourceCatalogMapper.selectCount(wrapper);
        if (hasExist > 0){
            return InterfaceResult.getError("目录名称已存在！");
        }
        String catalogCode = UUID.randomUUID().toString().replaceAll("-","");
        QueryWrapper<Dept> deptWrapper = new QueryWrapper<>();
        wrapper.eq("dept_code",catalog.getDeptCode());
        wrapper.eq("is_delete",1);
        Dept dept = deptService.getOne(deptWrapper);
        catalog.setDeptName(dept.getDeptName());
        catalog.setCatalogCode(catalogCode);
        if ("非格式化文件".equals(catalog.getIsFormat())){
            catalog.setIsComplete(1);
        }
        catalog.setCreateDept(userInfo.getDeptName());
        catalog.setCreateUser(userInfo.getUsername());
        catalog.setCreateTime(LocalDateTime.now());
        int insertStatus = resourceCatalogMapper.insert(catalog);
        if(insertStatus > 0){
            //目录基础信息创建成功,添加目录模板信息,创建归集库表
            if ("格式化文件".equals(catalog.getIsFormat())){
                try{
                    catalogTemplateService.addCatalogTemplateExcel(userInfo,excel,catalogCode);
                }catch (IOException e){
                    logger.info("addResourceCatalog======"+e);
                }
            }
            return InterfaceResult.getSuccess("创建成功",catalog);
        }

        return InterfaceResult.getError("创建失败");
    }

    public InterfaceResult<ResourceCatalog> updateResourceCatalog(UserInfo userInfo,ResourceCatalog catalog){
        QueryWrapper<ResourceCatalog> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog_code",catalog.getCatalogCode());
        wrapper.eq("is_delete",1);
        ResourceCatalog resourceCatalog = resourceCatalogMapper.selectOne(wrapper);
        if ( Constant.SUPER_USER_DEPT_CODE.equals(userInfo.getDeptCode()) || resourceCatalog.getDeptCode().equals(userInfo.getDeptCode())){
            catalog.setUpdateDept(userInfo.getDeptCode());
            catalog.setUpdateUser(userInfo.getUsername());
            catalog.setUpdateTime(LocalDateTime.now());
            int update = resourceCatalogMapper.update(catalog, wrapper);
            return InterfaceResult.hasExist(update,"修改成功","修改失败",catalog);
        }
        return InterfaceResult.getError("没有修改权限！");
    }

    public InterfaceResult<ResourceCatalog> deleteResourceCatalog(UserInfo userInfo,ResourceCatalog catalog){
        QueryWrapper<ResourceCatalog> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog_code",catalog.getCatalogCode());
        wrapper.eq("is_delete",1);
        ResourceCatalog resourceCatalog = resourceCatalogMapper.selectOne(wrapper);
        if ( Constant.SUPER_USER_DEPT_CODE.equals(userInfo.getDeptCode()) || resourceCatalog.getDeptCode().equals(userInfo.getDeptCode())){
            QueryWrapper<UploadRecord> recordWrapper = new QueryWrapper<>();
            recordWrapper.eq("catalog_code",catalog.getCatalogCode());
            recordWrapper.eq("upload_status",1);
            recordWrapper.eq("is_delete",1);
            if (uploadRecordService.count(recordWrapper) > 0){
                return InterfaceResult.getError("目录存在数据,无法删除!");
            }
            catalog.setIsDelete(0);
            catalog.setUpdateDept(userInfo.getDeptCode());
            catalog.setUpdateUser(userInfo.getUsername());
            catalog.setUpdateTime(LocalDateTime.now());
            int update = resourceCatalogMapper.update(catalog, wrapper);
            return InterfaceResult.hasExist(update,"删除成功","删除失败",null);
        }
        return InterfaceResult.getError("没有删除权限！");
    }

    public InterfaceResult<String> addCatalogData(UserInfo userInfo, String catalogCode,String uploadType, MultipartFile excel){
        LinkedList<LinkedList<Object>> templateList = Lists.newLinkedList();
        UploadRecord uploadRecord = new UploadRecord();
        String uploadTypeStr = "增量";
        boolean insertRecord = false;
        try {
            templateList = ExcelUtil.getExcelToList(excel.getInputStream(), Constant.EXCEL_FILE_TYPE);
            //查询模板,进行校验
            QueryWrapper<CatalogTemplate> templateWrapper = new QueryWrapper<>();
            templateWrapper.select("col_name","col_desc","col_type");
            templateWrapper.eq("catalog_code",catalogCode);
            templateWrapper.eq("is_delete",1);
            templateWrapper.last("order by orderby DESC, id ASC");
            List<CatalogTemplate> fieldList = catalogTemplateService.list(templateWrapper);
            if (templateList.size() > 1) {
                if (!this.checkTemplate(fieldList,templateList.get(0))) {
                    return InterfaceResult.getError("模板校验失败!");
                }
            }
            QueryWrapper<ResourceCatalog> wrapper = new QueryWrapper<>();
            wrapper.eq("catalog_code",catalogCode);
            wrapper.eq("is_delete",1);
            ResourceCatalog catalog = resourceCatalogService.getOne(wrapper);//获取表名
            if (catalog == null || catalog.getTableName() == null){
                return InterfaceResult.getError("目录查询失败/未生成归集库表,请先上传模板!");
            }
            insertRecord = true;
            //连接数据库
            Connection conn= DBMSMetaUtil.getConnection(Constant.RESOURCE_DATASOURCE.URL, Constant.RESOURCE_DATASOURCE.USERNAME,
                    Constant.RESOURCE_DATASOURCE.PASSWORD);
            if (conn == null){
                logger.info("数据库连接失败!");
                uploadRecord.setUploadStatus(0);
                uploadRecord.setErrorMsg("归集数据库连接失败!");
                return InterfaceResult.getError("数据库连接失败!");
            }
            //上传至云盘,保存上传记录
            JSONObject response = YunHelper.upload(excel.getBytes(), excel.getOriginalFilename());
            JSONObject body=JSONObject.parseObject(response.get("body").toString());
            uploadRecord.setFileUrl(YunHelper.getDownloadUrl(body.getString("infoid")));
            String tableName = catalog.getTableName();
            String dbType = conn.getMetaData().getDatabaseProductName();
            String sqlTemp = getInsertSqlTemp(templateList,fieldList,dbType,tableName);//拼接sql语句
            int errorLine = 0;
            conn.setAutoCommit(false);
            if (Constant.UPLOAD_TYPE.COVER.equals(uploadType)){//全量上传,先删除所有数据,并将之前的上传记录全改为失效
                uploadTypeStr = "全量";
                this.deleteDataByUploadCover(conn,dbType,tableName,catalogCode,userInfo);
            }
            PreparedStatement pstmt= conn.prepareStatement(sqlTemp);
            logger.info("++++++++++++模板打印开始++++++++++++++");
            logger.info(sqlTemp);
            logger.info("++++++++++++模板打印结束++++++++++++++");
            Long beginTime = System.currentTimeMillis();
            for(int i=1;i<templateList.size();i++){
                LinkedList<Object> ele = templateList.get(i);
                for(int j=0;j<ele.size();j++){
                    String colType = fieldList.get(j).getColType();
                    if (Constant.TEMP_TYPE.BIGINT.equals(colType) ){
                        pstmt.setLong(j+1,Long.parseLong(ele.get(j).toString()));
                    }else if (Constant.TEMP_TYPE.DECIMAL.equals(colType)){
                        pstmt.setBigDecimal(j+1,new BigDecimal(ele.get(j).toString()));
                    }else {
                        pstmt.setString(j+1, ele.get(j).toString());
                    }
                }
                pstmt.addBatch();
                if((i+1)%1000==0){//可以设置不同的大小；如50，100，500，1000等等，这里设置1000为一个批次
                    errorLine+=1000;
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                }
            }
            pstmt.executeBatch();
            conn.commit();
            Long endTime = System.currentTimeMillis();
            logger.info((templateList.size()-1)+"条数据入库用时："+(endTime-beginTime)+"毫秒");
            uploadRecord.setUploadStatus(1);
        }catch (Exception e){
            //错误信息保存到上传记录中
            uploadRecord.setUploadStatus(0);
            uploadRecord.setErrorMsg(e.toString());
            logger.info("addCatalogData:"+e);
            return InterfaceResult.getSuccess("操作失败!");
        }finally {
            if (insertRecord){
                uploadRecord.setCatalogCode(catalogCode);
                uploadRecord.setFileName(excel.getOriginalFilename());
                uploadRecord.setDataCount(templateList.size()-1);
                uploadRecord.setUploadType(uploadTypeStr);
                uploadRecord.setCreateUser(userInfo.getUsername());
                uploadRecord.setCreateDept(userInfo.getDeptName());
                uploadRecord.setCreateTime(LocalDateTime.now());
                uploadRecordService.save(uploadRecord);
            }
        }
        return InterfaceResult.getSuccess("操作成功!");
    }

    public InterfaceResult<String> addCatalogDataUnFormat(UserInfo userInfo,String catalogCode,MultipartFile file){
        boolean insertRecord = true;
        QueryWrapper<ResourceCatalog> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog_code",catalogCode);
        wrapper.eq("is_delete",1);
        wrapper.eq("is_format","非格式化文件");
        ResourceCatalog catalog = resourceCatalogMapper.selectOne(wrapper);
        if (catalog == null){
            insertRecord = false;
            return InterfaceResult.getError("目录不存在!");
        }
        UploadRecord record = new UploadRecord();
        //文件上传到云盘
        try{
            JSONObject response = YunHelper.upload(file.getBytes(), file.getOriginalFilename());
            JSONObject body=JSONObject.parseObject(response.get("body").toString());
            record.setFileUrl(YunHelper.getDownloadUrl(body.getString("infoid")));
            record.setFileName(file.getOriginalFilename());
            record.setUploadStatus(1);
        }catch (IOException ex){
            logger.info("addCatalogDataUnFormat======="+ex);
            record.setUploadStatus(0);
            record.setErrorMsg(ex.toString());
            return InterfaceResult.getError("文件上传云盘失败!");
        }finally {
            if (insertRecord){
                record.setCatalogCode(catalogCode);
                record.setCreateUser(userInfo.getUsername());
                record.setCreateDept(userInfo.getDeptName());
                record.setCreateTime(LocalDateTime.now());
                uploadRecordService.save(record);
            }
        }
        return InterfaceResult.getSuccess("上传成功!");
    }

    /**
     * 得到一个insert的模板
     */
    public static String getInsertSqlTemp(LinkedList<LinkedList<Object>> templateList,List<CatalogTemplate> fieldList,
                                          String dbType, String tableName){
        //给出sql前部不变内容
        StringBuilder headSql=new StringBuilder();
        headSql.append("insert into ").append(tableName).append("(");
        if("Oracle".equals(dbType)){
            headSql.append("id,");
        }
        for(int i=0;i<fieldList.size();i++){
            headSql.append(fieldList.get(i).getColName()).append(",");
        }
        headSql.append("insert_date");
        headSql.append(") values(");
        if("Oracle".equals(dbType)){
            headSql.append("seq_").append(templateList).append(".nextval,");
        }
        for(int i=0;i<fieldList.size();i++){
            headSql.append("?,");
        }
        if("Oracle".equals(dbType)){
            headSql.append("SYSTIMESTAMP");
        }else{
            headSql.append("now()");
        }
        headSql.append(")");
        return headSql.toString();
    }

    //全量上传,先删除所有数据,并将之前的上传记录全改为失效
    private void deleteDataByUploadCover(Connection conn,String dbType,String tableName,String catalogCode,UserInfo userInfo) throws SQLException {
        String deleteSql = "delete from " +tableName;
        if ("Oracle".equals(dbType)){
            deleteSql = deleteSql + " where 1=1 ";
        }
        Statement stmt = conn.createStatement();
        logger.info("全量上传先进行删数据操作,sql=============================="+deleteSql);
        stmt.execute(deleteSql);
        conn.commit();
        stmt.close();
        QueryWrapper<UploadRecord> recordWrapper = new QueryWrapper<>();
        recordWrapper.eq("catalog_code",catalogCode);
        recordWrapper.eq("upload_status",1);
        UploadRecord record = new UploadRecord();
        record.setUploadStatus(0);
        record.setUpdateDept(userInfo.getDeptName());
        record.setUpdateUser(userInfo.getUsername());
        record.setUpdateTime(LocalDateTime.now());
        uploadRecordService.update(record,recordWrapper);
    }

    private boolean checkTemplate(List<CatalogTemplate> templateList,LinkedList<Object> ele){
        if (templateList.size() == ele.size()){
            for (int index = 0; index< templateList.size(); index++){
                if (!templateList.get(index).getColDesc().equals(ele.get(index).toString())){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
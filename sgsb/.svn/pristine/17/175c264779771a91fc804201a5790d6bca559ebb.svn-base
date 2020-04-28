package com.zjhc.sgsb.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zjhc.sgsb.common.Constant;
import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.CatalogTemplate;
import com.zjhc.sgsb.entity.Dept;
import com.zjhc.sgsb.entity.ResourceCatalog;
import com.zjhc.sgsb.entity.UserInfo;
import com.zjhc.sgsb.mapper.CatalogTemplateMapper;
import com.zjhc.sgsb.service.ICatalogTemplateService;
import com.zjhc.sgsb.service.IResourceCatalogService;
import com.zjhc.sgsb.temp.NormalSqlTemp;
import com.zjhc.sgsb.temp.OracleSqlTemp;
import com.zjhc.sgsb.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 信息目录模板字段 服务实现类
 * </p>
 *
 * @author wusj
 * @since 2020-04-22
 */
@Service
@Transactional
public class CatalogTemplateServiceImpl extends ServiceImpl<CatalogTemplateMapper, CatalogTemplate> implements ICatalogTemplateService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static ExecutorService ES= Executors.newSingleThreadExecutor();

    @Autowired
    private CatalogTemplateMapper catalogTemplateMapper;

    @Autowired
    private ICatalogTemplateService catalogTemplateService;
    @Autowired
    private IResourceCatalogService resourceCatalogService;

    /**
     *  以表单形式添加模版表头
     * @param userInfo
     * @param templateList
     * @return
     */
    public InterfaceResult<String> addCatalogTemplatePage(UserInfo userInfo, List<CatalogTemplate> templateList){
        if (templateList != null && templateList.size() > 0){
            QueryWrapper<ResourceCatalog> wrapper = new QueryWrapper<>();
            wrapper.eq("catalog_code",templateList.get(0).getCatalogCode());
            wrapper.eq("is_delete",1);
            ResourceCatalog catalog = resourceCatalogService.getOne(wrapper);
            if (catalog == null || catalog.getIsComplete() == 1){
                return InterfaceResult.getError("模板已存在!");
            }
            Connection conn= DBMSMetaUtil.getConnection(Constant.RESOURCE_DATASOURCE.URL, Constant.RESOURCE_DATASOURCE.USERNAME,
                    Constant.RESOURCE_DATASOURCE.PASSWORD);
            if (conn == null){
                return InterfaceResult.getError("归集库连接失败!");
            }
            templateList.forEach( catalogTemplate -> {
                catalogTemplate.setCreateUser(userInfo.getUsername());
                catalogTemplate.setCreateDept(userInfo.getDeptName());
                catalogTemplate.setCreateTime(LocalDateTime.now());
            });
            catalogTemplateService.saveBatch(templateList);
            //数据库加表
//            dropAndCreateTable(datas, conn, userInfo.getDeptName(),catalog,listener.getMaxLength(),columnList);
            catalog.setIsComplete(1);
            catalog.setUpdateTime(LocalDateTime.now());
            catalog.setUpdateUser(userInfo.getUsername());
            catalog.setUpdateDept(userInfo.getDeptName());
            resourceCatalogService.update(catalog,wrapper);
            return InterfaceResult.getSuccess("操作成功!");
        }
        return InterfaceResult.getError("操作失败!");
    }

    /**
     *
     * @return  页面新增按钮 以excel形式上传模版
     * @throws IOException
     */
    public InterfaceResult<String> addCatalogTemplateExcel(UserInfo userInfo, MultipartFile excel,String catalogCode) throws IOException{
        QueryWrapper<ResourceCatalog> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog_code",catalogCode);
        wrapper.eq("is_delete",1);
        ResourceCatalog catalog = resourceCatalogService.getOne(wrapper);
        if (catalog == null || catalog.getIsComplete() == 1){
            return InterfaceResult.getError("目录查询失败/已生成模板,无需上传");
        }
        LinkedList<LinkedList<Object>> templateList = ExcelUtil.getExcelToList(excel.getInputStream(), Constant.EXCEL_FILE_TYPE);
        LinkedList<LinkedList<Object>> checkedList = this.checkFieldLength(templateList);
        //插入模板数据库
        List<CatalogTemplate> catalogTemplateList = this.packingTemplate(checkedList, catalogCode, userInfo, 1);
        catalogTemplateService.saveBatch(catalogTemplateList);
        String tableEname= PinYinHelper.getPinYinHeadChar(catalog.getDeptName()+"_"+catalogCode);
        if(tableEname.length()>=26){
            tableEname=tableEname.substring(0, 26);
        }
        //新建归集表
        Connection conn= DBMSMetaUtil.getConnection(Constant.RESOURCE_DATASOURCE.URL, Constant.RESOURCE_DATASOURCE.USERNAME,
                Constant.RESOURCE_DATASOURCE.PASSWORD);
        if (conn == null){
            logger.info("数据库连接失败!");
            return InterfaceResult.getError("数据库连接失败!");
        }
        createTable(checkedList, conn,tableEname);
        //excel文件保存到云盘
        JSONObject response = YunHelper.upload(excel.getBytes(), excel.getOriginalFilename());
        JSONObject body=JSONObject.parseObject(response.get("body").toString());
        //更新目录表信息
        catalog.setIsComplete(1);
        catalog.setTableName(tableEname);
        catalog.setTemplateUrl(YunHelper.getDownloadUrl(body.getString("infoid")));
        catalog.setUpdateDept(userInfo.getDeptName());
        catalog.setUpdateUser(userInfo.getUsername());
        catalog.setUpdateTime(LocalDateTime.now());
        resourceCatalogService.updateById(catalog);
        return InterfaceResult.getError("操作成功!");
    }

    /**
     * 下载模版
     * @param catalog
     * @param response
     * @return
     * @throws IOException
     */
    public InterfaceResult<byte[]> uploadTemplate(ResourceCatalog catalog, HttpServletResponse response) throws IOException{
        QueryWrapper<CatalogTemplate> wrapper = new QueryWrapper<>();
        wrapper.select("col_name","col_desc");
        wrapper.eq("catalog_code",catalog.getCatalogCode());
        wrapper.eq("is_delete",1);
        wrapper.last("order by orderby DESC, id ASC");
        List<CatalogTemplate> templateList = catalogTemplateService.list(wrapper);
        if (templateList == null || templateList.size() == 0){
            return InterfaceResult.getError("模板生成失败,未查询到有效字段!");
        }
        List<Map<String, Object>> template = Lists.newArrayList();
        templateList.forEach(catalogTemplate -> {
            Map<String, Object> ele = Maps.newHashMap();
            ele.put("col_desc",catalogTemplate.getColDesc());
            template.add(ele);
        });
        HSSFWorkbook tempExcel = ExcelUtil.createTempExcel(template);
        OutputStream output=response.getOutputStream();
        response.reset();
        response.setHeader("Content-disposition", "attachment; filename=template.xls");
        response.setContentType("application/msexcel");
        tempExcel.write(output);
        output.close();
        return null;
    }

    private LinkedList<LinkedList<Object>> checkFieldLength(LinkedList<LinkedList<Object>> templateList){
        for (int index = 1;  index<templateList.size();index++){
            LinkedList<Object> ele = templateList.get(index);//每一行元素
            if ("bigint".equals(ele.get(2).toString()) && Long.parseLong(ele.get(3).toString()) > Long.parseLong(Constant.FIELD_MAX.BIGINT)){
                ele.set(3,Constant.FIELD_MAX.BIGINT);
            }else if ("varchar".equals(ele.get(2).toString()) && Long.parseLong(ele.get(3).toString()) > Long.parseLong(Constant.FIELD_MAX.VARCHAR)){
                ele.set(3,Constant.FIELD_MAX.VARCHAR);
            }else if ("decimal".equals(ele.get(2).toString()) && Long.parseLong(ele.get(3).toString()) > Long.parseLong(Constant.FIELD_MAX.DECIMAL)){
                ele.set(3,Constant.FIELD_MAX.DECIMAL);
            }else if ("text".equals(ele.get(2).toString()) && Long.parseLong(ele.get(3).toString()) > Long.parseLong(Constant.FIELD_MAX.TEXT)){
                ele.set(3,Constant.FIELD_MAX.TEXT);
            }
        }
        return templateList;
    }

    /**
     * 将目标表先删除，再重建
     * @param excel 上传的数据
     * @param conn	数据库连接
     * @param deptName 部门名
     */
    private void createTable(LinkedList<LinkedList<Object>> excel,Connection conn,String tableName){
        String dbType;
        try {
            conn.setAutoCommit(false);
            dbType = conn.getMetaData().getDatabaseProductName();
//            String dropSql= NormalSqlTemp.dropTableSql(tableEname,dbType);
            String createTableSql=NormalSqlTemp.CreateTableSql(tableName, dbType, excel);
            logger.info("SQL="+createTableSql);
            Statement stmt=conn.createStatement();
//            try {
//                stmt.execute(dropSql);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            logger.info(createTableSql);
            stmt.execute(createTableSql);
//            if("Oracle".equals(dbType)){
//                stmt.execute(OracleSqlTemp.returnSequenceSql(tableEname));
//            }
            conn.commit();
            stmt.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private List<CatalogTemplate> packingTemplate(LinkedList<LinkedList<Object>> checkedList,String catalogCode,UserInfo info,int startIndex){
        List<CatalogTemplate> result = Lists.newArrayList();
        for (int index = startIndex;index<checkedList.size();index++){
            LinkedList<Object> ele = checkedList.get(index);
            CatalogTemplate template = new CatalogTemplate();
            template.setCatalogCode(catalogCode);
            template.setColName(ele.get(0).toString());
            template.setColDesc(ele.get(1).toString());
            template.setColType(ele.get(2).toString());
            template.setColLength(Integer.parseInt(ele.get(3).toString()));
            template.setColPrecesion(Integer.parseInt(ele.get(4).toString()));
            template.setCreateUser(info.getUsername());
            template.setCreateDept(info.getDeptName());
            template.setCreateTime(LocalDateTime.now());
            result.add(template);
        }
        return result;
    }


}

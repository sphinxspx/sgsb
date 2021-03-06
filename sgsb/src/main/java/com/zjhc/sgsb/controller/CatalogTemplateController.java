package com.zjhc.sgsb.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.CatalogTemplate;
import com.zjhc.sgsb.entity.ResourceCatalog;
import com.zjhc.sgsb.entity.UserInfo;
import com.zjhc.sgsb.service.ICatalogTemplateService;
import com.zjhc.sgsb.service.IUserInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 信息目录模板字段 前端控制器
 * </p>
 *
 * @author wusj
 * @since 2020-04-22
 */
@CrossOrigin
@RestController
@RequestMapping("/sgsb/catalog-template")
public class CatalogTemplateController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICatalogTemplateService catalogTemplateService;
    @Autowired
    private IUserInfoService userInfoService;

    @ApiOperation(value="用户定义模板字段保存:页面输入字段")
    @RequestMapping(value = "add_catalog_template_page")
    @CrossOrigin
    public InterfaceResult<String> addCatalogTemplatePage(String token, @RequestBody JSONObject jsonArray) {
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (!authentication.issuccess()){
            return InterfaceResult.getError(authentication.getMsg());
        }
        UserInfo userInfo = authentication.getData();

        String templateDate = jsonArray.toJSONString();
        //解析json数据
        JSONObject jsonObject = JSON.parseObject(templateDate);
        String catalogCode = jsonObject.getString("catalogCode");
        String colList = jsonObject.getString("colList");
        List<CatalogTemplate> catalogTemplateList = new ArrayList<>();
        if(StringUtils.isNotEmpty(catalogCode)&& StringUtils.isNotEmpty(colList)){
            JSONArray colArray = JSONArray.parseArray(colList);
            for (int i = 0; i < colArray.size(); i++) {
                CatalogTemplate catalogTemplate = new CatalogTemplate();
                String col_name = JSONObject.parseObject(JSONObject.toJSONString(colArray.get(i))).getString("colName");
                String col_desc = JSONObject.parseObject(JSONObject.toJSONString(colArray.get(i))).getString("colDesc");
                catalogTemplate.setColName(col_name);
                catalogTemplate.setColDesc(col_desc);
                catalogTemplate.setCatalogCode(catalogCode);
                catalogTemplateList.add(catalogTemplate);
            }
        }
        return catalogTemplateService.addCatalogTemplatePage(userInfo,catalogTemplateList);
    }

    /**
     * 未使用
     */
    @ApiOperation(value="用户定义模板字段保存:excel解析,并创建归集表")
    @RequestMapping(value = "add_catalog_template_excel")
    public InterfaceResult<String> addCatalogTemplateExcel(MultipartFile file) throws IOException{
        String token = "eyJjcmVhdGVUaW1lIjoiMjAyMC0wNC0yMFQxMzo1ODowMCIsImRlcHRDb2RlIjoiYWRtaW5pc3RyYXRvciIsImRlcHROYW1l" +
                "Ijoi6LaF57qn566h55CG5ZGYIiwiaWQiOjEsImlzRGVsZXRlIjoxLCJpc1N1cGVyVXNlciI6MSwib3JkZXJieSI6OTksInBhc3N3b3J" +
                "kIjoiZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2UiLCJ1c2VybmFtZSI6ImFkbWluIn0=";
        String catalogCode = "8226604cbeea44c0ad754432bd64f91f";
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (!authentication.issuccess()){
            return InterfaceResult.getError(authentication.getMsg());
        }
        UserInfo userInfo = authentication.getData();
        return catalogTemplateService.addCatalogTemplateExcel(userInfo,file,catalogCode);
    }

    @ApiOperation(value="下载模板")
    @RequestMapping(value = "upload_template")
    public InterfaceResult<byte[]> uploadTemplate(String token,ResourceCatalog catalog, HttpServletResponse response) throws IOException {
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (!authentication.issuccess()){
            return InterfaceResult.getError(authentication.getMsg());
        }
        return catalogTemplateService.uploadTemplate(catalog,response);
    }

}

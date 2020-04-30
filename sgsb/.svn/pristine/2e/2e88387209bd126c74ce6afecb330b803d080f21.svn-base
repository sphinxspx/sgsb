package com.zjhc.sgsb.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjhc.sgsb.common.Constant;
import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.CatalogTemplate;
import com.zjhc.sgsb.entity.ResourceCatalog;
import com.zjhc.sgsb.entity.UserInfo;
import com.zjhc.sgsb.service.IResourceCatalogService;
import com.zjhc.sgsb.service.IUserInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpRequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import com.zjhc.sgsb.controller.BaseController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wusj
 * @since 2020-04-19
 */
@CrossOrigin
@RestController
@RequestMapping("/sgsb/resource-catalog")
public class ResourceCatalogController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IResourceCatalogService resourceCatalogService;
    @Autowired
    private IUserInfoService userInfoService;

    @ApiOperation(value="根据部门code获取资源(可根据目录名称模糊查询)")
    @RequestMapping(value = "list_resource_catalog")
    public InterfaceResult<List<ResourceCatalog>> listResourceCatalog(String token,ResourceCatalog catalog,Integer pageIndex,Integer pageSize){
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (!authentication.issuccess()){
            return InterfaceResult.getError("请先登录!"+authentication.getMsg());
        }
        UserInfo userInfo = authentication.getData();
        Page<ResourceCatalog> page = new Page<>(pageIndex,pageSize);
        return resourceCatalogService.listResourceCatalog(userInfo,catalog,page);
    }

    @ApiOperation(value="创建资源,格式化文件需要file进行归集库表创建操作")
    @RequestMapping(value = "add_resource_catalog")
    public InterfaceResult<ResourceCatalog> addResourceCatalog(String token, ResourceCatalog catalog,MultipartFile file) {
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (! authentication.issuccess()){
            return InterfaceResult.getError("请先登录");
        }
        return resourceCatalogService.addResourceCatalog(authentication.getData(),catalog,file);
    }

    @ApiOperation(value="修改资源")
    @RequestMapping(value = "update_resource_catalog")
    public InterfaceResult<ResourceCatalog> updateResourceCatalog(String token,ResourceCatalog catalog){
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (! authentication.issuccess()){
            return InterfaceResult.getError("请先登录");
        }
        return resourceCatalogService.updateResourceCatalog(authentication.getData(),catalog);
    }

    @ApiOperation(value="删除资源")
    @RequestMapping(value = "delete_resource_catalog")
    public InterfaceResult<ResourceCatalog> deleteResourceCatalog(String token,ResourceCatalog catalog){
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (! authentication.issuccess()){
            return InterfaceResult.getError("请先登录");
        }
        return resourceCatalogService.deleteResourceCatalog(authentication.getData(),catalog);
    }

    @ApiOperation(value="格式化文件目录上传:excel解析保存数据,保存数据到归集表(增量)")
    @RequestMapping(value = "add_resource_data")
    public InterfaceResult<String> addCatalogData(String token, String catalogCode,String uploadType, MultipartFile file) throws Exception {
        token = "eyJjcmVhdGVUaW1lIjoiMjAyMC0wNC0yMFQxMzo1ODowMCIsImRlcHRDb2RlIjoiYWRtaW5pc3RyYXRvciIsImRlcHROYW1l" +
                "Ijoi6LaF57qn566h55CG5ZGYIiwiaWQiOjEsImlzRGVsZXRlIjoxLCJpc1N1cGVyVXNlciI6MSwib3JkZXJieSI6OTksInBhc3N3b3J" +
                "kIjoiZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2UiLCJ1c2VybmFtZSI6ImFkbWluIn0=";
        catalogCode = "8226604cbeea44c0ad754432bd64f91f";
        uploadType = Constant.UPLOAD_TYPE.COVER;//增量,全量标识
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (!authentication.issuccess()){
            return InterfaceResult.getError(authentication.getMsg());
        }
        UserInfo userInfo = authentication.getData();
        return resourceCatalogService.addCatalogData(userInfo,catalogCode,uploadType,file);
    }

    @ApiOperation(value="非格式化文件目录上传:文件保存")
    @RequestMapping(value = "add_resource_data_unformat")
    public InterfaceResult<String> addCatalogDataUnFormat(String token, String catalogCode,MultipartFile file) throws Exception {
        token = "eyJjcmVhdGVUaW1lIjoiMjAyMC0wNC0yMFQxMzo1ODowMCIsImRlcHRDb2RlIjoiYWRtaW5pc3RyYXRvciIsImRlcHROYW1l" +
                "Ijoi6LaF57qn566h55CG5ZGYIiwiaWQiOjEsImlzRGVsZXRlIjoxLCJpc1N1cGVyVXNlciI6MSwib3JkZXJieSI6OTksInBhc3N3b3J" +
                "kIjoiZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2UiLCJ1c2VybmFtZSI6ImFkbWluIn0=";
        catalogCode = "111111";
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (!authentication.issuccess()){
            return InterfaceResult.getError(authentication.getMsg());
        }
        UserInfo userInfo = authentication.getData();
        return resourceCatalogService.addCatalogDataUnFormat(userInfo,catalogCode,file);
    }


}

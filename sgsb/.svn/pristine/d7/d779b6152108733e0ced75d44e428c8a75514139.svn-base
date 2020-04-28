package com.zjhc.sgsb.controller;


import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.Dept;
import com.zjhc.sgsb.entity.UserInfo;
import com.zjhc.sgsb.service.IDeptService;
import com.zjhc.sgsb.service.IUserInfoService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.zjhc.sgsb.controller.BaseController;

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
@RequestMapping("/sgsb/dept")
public class DeptController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserInfoService userInfoService;

    @ApiOperation(value="新增部门+新增用户")
    @RequestMapping(value = "add_dept_user")
    public InterfaceResult<String> addDeptUser(String token, Dept dept,UserInfo userInfo){
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (!authentication.issuccess()){
            return InterfaceResult.getError(authentication.getMsg());
        }
        if (authentication.getData().getIsSuperUser() != 1 ){
            return InterfaceResult.getError("没有权限！");
        }
        return deptService.addDeptUser(dept,userInfo);
    }

    @ApiOperation(value="删除部门")
    @RequestMapping(value = "delete_dept")
    public InterfaceResult<String> deleteDept(String token, Dept dept){
        InterfaceResult<UserInfo> authentication = userInfoService.authentication(token);
        if (!authentication.issuccess()){
            return InterfaceResult.getError(authentication.getMsg());
        }
        if (authentication.getData().getIsSuperUser() != 1 ){
            return InterfaceResult.getError("没有删除部门权限！");
        }
        return deptService.deleteDept(dept);
    }

    @ApiOperation(value="获取所有部门code-name")
    @RequestMapping(value = "list_dept")
    public InterfaceResult<List<Dept>> listDept(String searchInfo){
        return deptService.listDept(searchInfo);
    }
}

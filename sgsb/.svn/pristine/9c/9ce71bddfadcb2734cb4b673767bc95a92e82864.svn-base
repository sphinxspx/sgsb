package com.zjhc.sgsb.service;

import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjhc.sgsb.entity.UserInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wusj
 * @since 2020-04-19
 */
public interface IDeptService extends IService<Dept> {

    InterfaceResult<String> addDeptUser(Dept dept, UserInfo userInfo) ;

    InterfaceResult<String> deleteDept(Dept dept);

    InterfaceResult<List<Dept>> listDept(String searchInfo);



}

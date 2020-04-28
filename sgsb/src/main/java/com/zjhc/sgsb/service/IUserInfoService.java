package com.zjhc.sgsb.service;

import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wusj
 * @since 2020-04-19
 */
public interface IUserInfoService extends IService<UserInfo> {

    InterfaceResult<String> login(UserInfo userInfo);

    InterfaceResult<String> deleteUser(UserInfo userInfo);

    InterfaceResult<String> forgetPassword(UserInfo userInfo);

    InterfaceResult<String> resetPassword(UserInfo userInfo,String newPassword);

    InterfaceResult<UserInfo> authentication(String token);

}

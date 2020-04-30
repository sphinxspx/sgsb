package com.zjhc.sgsb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.CatalogTemplate;
import com.zjhc.sgsb.entity.ResourceCatalog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjhc.sgsb.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wusj
 * @since 2020-04-19
 */
public interface IResourceCatalogService extends IService<ResourceCatalog> {

    InterfaceResult<List<ResourceCatalog>> listResourceCatalog(UserInfo userInfo,ResourceCatalog catalog,Page<ResourceCatalog> page);

    InterfaceResult<ResourceCatalog> addResourceCatalog(UserInfo userInfo, ResourceCatalog catalog,MultipartFile file);

    InterfaceResult<ResourceCatalog> updateResourceCatalog(UserInfo userInfo,ResourceCatalog catalog);

    InterfaceResult<ResourceCatalog> deleteResourceCatalog(UserInfo userInfo,ResourceCatalog catalog);

    InterfaceResult<String> addCatalogData(UserInfo userInfo, String catalogCode,String uploadType, MultipartFile excel) ;

    InterfaceResult<String> addCatalogDataUnFormat(UserInfo userInfo,String catalogCode,MultipartFile file);

}

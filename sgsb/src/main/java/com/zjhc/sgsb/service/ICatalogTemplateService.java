package com.zjhc.sgsb.service;

import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.CatalogTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjhc.sgsb.entity.ResourceCatalog;
import com.zjhc.sgsb.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 信息目录模板字段 服务类
 * </p>
 *
 * @author wusj
 * @since 2020-04-22
 */
public interface ICatalogTemplateService extends IService<CatalogTemplate> {

    InterfaceResult<String> addCatalogTemplatePage(UserInfo userInfo, List<CatalogTemplate> templateList) ;

    InterfaceResult<String> addCatalogTemplateExcel(UserInfo userInfo, MultipartFile excel,String catalogCode) throws IOException;

    InterfaceResult<byte[]> uploadTemplate(ResourceCatalog catalog, HttpServletResponse response) throws IOException;

}

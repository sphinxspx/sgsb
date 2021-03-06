package com.zjhc.sgsb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjhc.sgsb.common.InterfaceResult;
import com.zjhc.sgsb.entity.UploadRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjhc.sgsb.entity.vo.UploadRecordVo;

import java.util.List;

/**
 * <p>
 * 上传记录 服务类
 * </p>
 *
 * @author wusj
 * @since 2020-04-22
 */
public interface IUploadRecordService extends IService<UploadRecord> {

    InterfaceResult<List<UploadRecordVo>> listUploadRecordByCatalogCode(String catalogCode, Page<UploadRecord> page);

}

package com.zjhc.sgsb.entity;
import com.zjhc.sgsb.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 上传记录
 * </p>
 *
 * @author wusj
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sgsb_upload_record")
@ApiModel(value="UploadRecord对象", description="上传记录")
public class UploadRecord extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String catalogCode;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    private String fileUrl;

    @ApiModelProperty(value = "数据量")
    private Integer dataCount;

    @ApiModelProperty(value = "上传状态 0失败 1成功")
    private Integer uploadStatus;

    @ApiModelProperty(value = "上传状态")
    private String uploadType;

    private String errorMsg;

    private Integer isDelete;

    private Integer orderby;

    private String createUser;

    private String createDept;

    private LocalDateTime createTime;

    private String updateUser;

    private String updateDept;

    private LocalDateTime updateTime;

}

package com.zjhc.sgsb.entity;
import com.zjhc.sgsb.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 
 * </p>
 *
 * @author wusj
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sgsb_resource_catalog")
@ApiModel(value="ResourceCatalog对象", description="")
public class ResourceCatalog extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String deptCode;

    private String deptName;

    private String catalogCode;

    @ApiModelProperty(value = "目录名称")
    private String catalogName;

    @ApiModelProperty(value = "目录信息")
    private String catalogIntro;

    @ApiModelProperty(value = "归集库表名")
    private String tableName;

    private String templateUrl;

    @ApiModelProperty(value = "目录是否已创建")
    private Integer isComplete;


    @ApiModelProperty(value = "是否格式化文件")
    private String isFormat;

    private Integer isDelete;

    private Integer orderby;

    private String createUser;

    private String createDept;

    private LocalDateTime createTime;

    private String updateUser;

    private String updateDept;

    private LocalDateTime updateTime;

}

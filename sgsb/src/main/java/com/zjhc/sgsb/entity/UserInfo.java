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
 * 
 * </p>
 *
 * @author wusj
 * @since 2020-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sgsb_user_info")
@ApiModel(value="UserInfo对象", description="")
public class UserInfo extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

    @ApiModelProperty(value = "部门code")
    private String deptCode;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "是否超级用户")
    private Integer isSuperUser;

    @ApiModelProperty(value = "删除标记")
    private Integer isDelete;

    @ApiModelProperty(value = "排序标记")
    private Integer orderby;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}

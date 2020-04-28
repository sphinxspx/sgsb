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
 * 信息目录模板字段
 * </p>
 *
 * @author wusj
 * @since 2020-04-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sgsb_catalog_template")
@ApiModel(value="CatalogTemplate对象", description="信息目录模板字段")
public class CatalogTemplate extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String catalogCode;

    private String colName;

    private String colDesc;

    private String colType;

    private Integer colLength;

    private Integer colPrecesion;

    private Integer isDelete;

    private Integer orderby;

    private String createUser;

    private String createDept;

    private LocalDateTime createTime;

    private String updateUser;

    private String updateDept;

    private LocalDateTime updateTime;

}

package com.group13.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
 * @author group13
 * @since 2022-03-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="CommodityComment对象", description="")
public class CommodityComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "unique identification")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "content")
    private String content;

    @ApiModelProperty(value = "commodity_id")
    private String commodityId;

    @ApiModelProperty(value = "user_id")
    private String userId;

    @ApiModelProperty(value = "like_amount")
    private Integer likeAmount;

    @ApiModelProperty(value = "created time")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "modified time")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModify;


}

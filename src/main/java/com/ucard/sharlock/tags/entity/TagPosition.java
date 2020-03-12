package com.ucard.sharlock.tags.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wusk
 * @since 2020-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)  
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)//在自增主键的变量加上即可
    private Integer Id;
    /**
     * TagID
     */
    private String tagId;

    /**
     * 位置更新时间
     */
    @TableField("tag_positionTS")
    private Long tagPositionts;

    /**
     * smoothedX位置
     */
    @TableField("tag_smoothedPostionX")
    private BigDecimal tagSmoothedpostionx;

    /**
     * smoothedY位置
     */
    @TableField("tag_smoothedPostionY")
    private BigDecimal tagSmoothedpostiony;

    /**
     * smoothedZ位置
     */
    @TableField("tag_smoothedPostionZ")
    private BigDecimal tagSmoothedpostionz;

    /**
     * smoothed位置精度
     */
    @TableField("tag_smoothedPostionAccuracy")
    private BigDecimal tagSmoothedpostionaccuracy;

    /**
     * X位置
     */
    @TableField("tag_postionX")
    private BigDecimal tagPostionx;

    /**
     * Y位置
     */
    @TableField("tag_postionY")
    private BigDecimal tagPostiony;

    /**
     * Z位置
     */
    @TableField("tag_postionZ")
    private BigDecimal tagPostionz;

    /**
     * 位置精度
     */
    @TableField("tag_postionAccuracy")
    private BigDecimal tagPostionaccuracy;

    /**
     * 协方差XX
     */
    @TableField("tag_covarianceMatrixXX")
    private BigDecimal tagCovariancematrixxx;

    /**
     * 协方差XY
     */
    @TableField("tag_covarianceMatrixXY")
    private BigDecimal tagCovariancematrixxy;

    /**
     * 协方差YX
     */
    @TableField("tag_covarianceMatrixYX")
    private BigDecimal tagCovariancematrixyx;

    /**
     * 协方差YY
     */
    @TableField("tag_covarianceMatrixYY")
    private BigDecimal tagCovariancematrixyy;


}

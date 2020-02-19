package com.ucard.sharlock.tags.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

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
public class TagBaseinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)//在自增主键的变量加上即可
    private Integer Id;
    /**
     * TagID
     */
    private String tagId;

    /**
     * 名字
     */
    private String tagName;

    /**
     * 颜色
     */
    private String tagColor;

    /**
     * 设备类型
     */
    @TableField("tag_deviceType")
    private String tagDevicetype;

    /**
     * 分组
     */
    private String tagGroup;


}

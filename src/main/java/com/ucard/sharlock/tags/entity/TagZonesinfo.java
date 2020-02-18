package com.ucard.sharlock.tags.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class TagZonesinfo implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * zoneID
     */
    private String tagZoneid;

    /**
     * zone名字
     */
    private String tagZonename;


}

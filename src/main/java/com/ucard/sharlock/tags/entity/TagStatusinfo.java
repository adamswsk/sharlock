package com.ucard.sharlock.tags.entity;

import java.math.BigDecimal;
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
public class TagStatusinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * TagID
     */
    private String tagId;

    /**
     * 最后包时间
     */
    @TableField("tag_lastpacketTS")
    private Long tagLastpacketts;

    /**
     * 电量
     */
    @TableField("tag_batteryVoltage")
    private BigDecimal tagBatteryvoltage;

    /**
     * 电量更新时间
     */
    @TableField("tag_batteryVoltageTS")
    private Long tagBatteryvoltagets;

    /**
     * 电量报警
     */
    @TableField("tag_batteryAlarm")
    private String tagBatteryalarm;

    /**
     * 电量报警时间
     */
    @TableField("tag_batteryAlarmTS")
    private Long tagBatteryalarmts;

    /**
     * 按钮状态
     */
    @TableField("tag_buttonState")
    private String tagButtonstate;

    /**
     * 按钮状态更新时间
     */
    @TableField("tag_buttonStateTS")
    private Long tagButtonstatets;

    /**
     * 按钮按压时间
     */
    @TableField("tag_lastButtonPressTS")
    private Long tagLastbuttonpressts;

    /**
     * 按钮2按压时间
     */
    @TableField("tag_lastButton2PressTS")
    private Long tagLastbutton2pressts;

    /**
     * 信号强度
     */
    private BigDecimal tagRssi;

    /**
     * 信号强度更新时间
     */
    @TableField("tag_rssiTS")
    private Long tagRssits;

    /**
     * 基站ID
     */
    @TableField("tag_rssiLocator")
    private String tagRssilocator;

    /**
     * 信号速率
     */
    private BigDecimal tagTxrate;

    /**
     * 信号速率更新时间
     */
    @TableField("tag_txrateTS")
    private Long tagTxratets;

    /**
     * 信号能量
     */
    private BigDecimal tagTxpower;

    /**
     * 信号能量更新时间
     */
    @TableField("tag_txpowerTS")
    private Long tagTxpowerts;


}

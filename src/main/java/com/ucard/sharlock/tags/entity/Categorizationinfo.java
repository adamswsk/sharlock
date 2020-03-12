package com.ucard.sharlock.tags.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wusk
 * @since 2020-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Categorizationinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 岗位
     */
    private String userPost;

    /**
     * 分组
     */
    private String tagGroup;

    /**
     * 颜色
     */
    private String tagColor;
    /**
     * 配置
     */
    private String tagConfig;


}

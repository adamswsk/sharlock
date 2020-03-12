package com.ucard.sharlock.tags.entity;

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
 * @since 2020-03-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * TagID
     */
    private String tagId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 身份证
     */
    private String userId;

    /**
     * 岗位
     */
    private String userPost;
    
    /**
     * 是否已登记
     */
    private boolean station;

}

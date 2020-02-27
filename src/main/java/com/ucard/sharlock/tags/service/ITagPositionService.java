package com.ucard.sharlock.tags.service;

import java.util.List;

import com.ucard.sharlock.tags.entity.TagPosition;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wusk
 * @since 2020-02-09
 */
public interface ITagPositionService extends IService<TagPosition> {

	List<TagPosition> findpositionbytagid(String tagid);
}

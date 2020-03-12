package com.ucard.sharlock.tags.mapper;

import java.util.List;

import com.ucard.sharlock.tags.entity.TagPosition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wusk
 * @since 2020-02-09
 */
public interface TagPositionMapper extends BaseMapper<TagPosition> {

	List<TagPosition> findpositionbytagid(String tagid);
	List<TagPosition> findposbyidandtime(String tagid,Long BeginPositionTS,Long EndPositionTS);
}

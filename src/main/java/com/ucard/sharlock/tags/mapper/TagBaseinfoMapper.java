package com.ucard.sharlock.tags.mapper;

import java.util.List;

import com.ucard.sharlock.tags.entity.TagBaseinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wusk
 * @since 2020-02-09
 */
public interface TagBaseinfoMapper extends BaseMapper<TagBaseinfo> {

	/**
	 * 
	 * @return
	 */
	public abstract List<TagBaseinfo> findAll();
	
}

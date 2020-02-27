package com.ucard.sharlock.tags.service.impl;

import java.util.List;

import com.ucard.sharlock.tags.entity.TagPosition;
import com.ucard.sharlock.tags.mapper.TagPositionMapper;
import com.ucard.sharlock.tags.service.ITagPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wusk
 * @since 2020-02-09
 */
@Service
public class TagPositionServiceImpl extends ServiceImpl<TagPositionMapper, TagPosition> implements ITagPositionService {

	@Autowired
	private TagPositionMapper tagPositionMapper;
	
	@Override
	public List<TagPosition> findpositionbytagid(String tagid) {
		return tagPositionMapper.findpositionbytagid(tagid);
	}

}

package com.ucard.sharlock.tags.service.impl;

import java.util.List;

import com.ucard.sharlock.tags.entity.TagBaseinfo;
import com.ucard.sharlock.tags.mapper.TagBaseinfoMapper;
import com.ucard.sharlock.tags.service.ITagBaseinfoService;
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
public class TagBaseinfoServiceImpl extends ServiceImpl<TagBaseinfoMapper, TagBaseinfo> implements ITagBaseinfoService {

	@Autowired
	private TagBaseinfoMapper tagBaseinfoMapper;
	
	@Override
	public List<TagBaseinfo> findAll() {
		//return (List<TagBaseinfo>) tagBaseinfoMapper.selectList(null);
		return (List<TagBaseinfo>) tagBaseinfoMapper.findAll();
	}
}

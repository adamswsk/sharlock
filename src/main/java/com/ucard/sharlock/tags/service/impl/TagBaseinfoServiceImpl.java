package com.ucard.sharlock.tags.service.impl;

import java.util.List;

import com.ucard.sharlock.core.page.MybatisPageHelper;
import com.ucard.sharlock.core.page.PageRequest;
import com.ucard.sharlock.core.page.PageResult;
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

	@Override
	public PageResult findPage(PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return MybatisPageHelper.findPage(pageRequest, tagBaseinfoMapper);
	}

	@Override
	public List<TagBaseinfo> findinfobytagid(String tagid) {
		// TODO Auto-generated method stub
		return (List<TagBaseinfo>) tagBaseinfoMapper.findinfobytagid(tagid);
	}

	@Override
	public List<TagBaseinfo> findinfobyname(String tagname) {
		// TODO Auto-generated method stub
		return (List<TagBaseinfo>) tagBaseinfoMapper.findinfobyname(tagname);
	}
}

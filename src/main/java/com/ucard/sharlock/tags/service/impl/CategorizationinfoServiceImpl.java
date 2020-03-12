package com.ucard.sharlock.tags.service.impl;

import java.util.List;

import com.ucard.sharlock.tags.entity.Categorizationinfo;
import com.ucard.sharlock.tags.mapper.CategorizationinfoMapper;
import com.ucard.sharlock.tags.service.ICategorizationinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wusk
 * @since 2020-03-11
 */
@Service
public class CategorizationinfoServiceImpl extends ServiceImpl<CategorizationinfoMapper, Categorizationinfo> implements ICategorizationinfoService {

	@Autowired
	private CategorizationinfoMapper categorizationinfo;
	
	@Override
	public List<Categorizationinfo> getpostlist() {
		
		return categorizationinfo.getpostlist();
	}

}

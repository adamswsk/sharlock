package com.ucard.sharlock.tags.service;

import java.util.List;

import com.ucard.sharlock.tags.entity.TagBaseinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wusk
 * @since 2020-02-09
 */
public interface ITagBaseinfoService extends IService<TagBaseinfo> {

	/**
	 * 
	 * @return
	 */
	public abstract List<TagBaseinfo> findAll();
	
}

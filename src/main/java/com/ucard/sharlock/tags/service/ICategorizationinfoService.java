package com.ucard.sharlock.tags.service;

import java.util.List;

import com.ucard.sharlock.tags.entity.Categorizationinfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wusk
 * @since 2020-03-11
 */
public interface ICategorizationinfoService extends IService<Categorizationinfo> {

	List<Categorizationinfo> getpostlist();
}

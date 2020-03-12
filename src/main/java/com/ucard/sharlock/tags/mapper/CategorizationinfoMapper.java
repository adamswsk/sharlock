package com.ucard.sharlock.tags.mapper;

import java.util.List;

import com.ucard.sharlock.tags.entity.Categorizationinfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wusk
 * @since 2020-03-11
 */
public interface CategorizationinfoMapper extends BaseMapper<Categorizationinfo> {

	List<Categorizationinfo> getpostlist();
}

package com.ucard.sharlock.tags.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ucard.sharlock.core.http.HttpResult;
import com.ucard.sharlock.core.page.PageRequest;
import com.ucard.sharlock.tags.service.ITagBaseinfoService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wusk
 * @since 2020-02-09
 */
@RestController
@RequestMapping("/tags/tag-baseinfo")
public class TagBaseinfoController {
	@Autowired
	private ITagBaseinfoService itagBaseinfoService;

	@GetMapping(value="/findAll")
	public Object findAll()
	{
		return itagBaseinfoService.findAll();
	}
	
	@GetMapping(value="/findPage")
	public HttpResult findPage(PageRequest pageRequest)
	{
		return HttpResult.ok(itagBaseinfoService.findPage(pageRequest));	
	}
}

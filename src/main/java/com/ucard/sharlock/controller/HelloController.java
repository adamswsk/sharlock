package com.ucard.sharlock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ucard.sharlock.tags.service.TestService;

@RestController
public class HelloController {
	
	@Autowired
	private TestService testService;
	
	@GetMapping(value="/hello")
	public Object hello()
	{
		return "hello sharlock!";
	}
	
	@GetMapping(value="/test")
	public JSONObject request()
	{
		return testService.getEngineMesasge("true");
	}
	
}

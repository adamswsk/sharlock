package com.ucard.sharlock.tags.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

@FeignClient(url = "${decisionEngine.url}",name="testengine")
public interface TestService {
	@RequestMapping(value="/qpe/getPEInfo",method= RequestMethod.POST)
	public JSONObject getEngineMesasge(@RequestParam("humanReadable") String humanReadable);
}
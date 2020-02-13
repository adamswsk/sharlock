package com.ucard.sharlock;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ucard.sharlock.tags.service.ILocationService;
import com.ucard.sharlock.tags.service.ITagAreainfoService;
import com.ucard.sharlock.tags.service.ITagPositionService;
import com.ucard.sharlock.tags.service.ITagZonesinfoService;


@Component
@Order(value = 1)
public class TagPositionThread implements CommandLineRunner {

	@Autowired
	private ILocationService locationService;
	
	@Autowired
	private ITagPositionService tagPositionService;
	
	@Autowired
	private ITagZonesinfoService tagZonesinfoService;
	
	@Autowired
	private ITagAreainfoService tagAreainfoService;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		JSONObject result = locationService.getTagPosition("2", null, null, "5000", null, null, "true", null, null, null);
		Timestamp responseTS = result.getTimestamp("responseTS");
		System.out.println("时间： "+responseTS);
		JSONArray tags = result.getJSONArray("tags");
		if(tags==null)
			return;
		for(int i=0;i<tags.size();i++)
		{
			JSONObject tag= (JSONObject) tags.get(i);
			
			String id = tag.getString("id");
			Timestamp positionTS = tag.getTimestamp("positionTS");
			System.out.println(positionTS);
			//tag_position表内容
			Object sp = tag.get("smoothedPosition");
			System.out.println(sp);
			double spAccuracy = tag.getDoubleValue("smoothedPositionAccuracy");
			Object p = tag.get("position");
			double pAccuracy = tag.getDoubleValue("positionAccuracy");
			Object covarianceMatrix = tag.get("covarianceMatrix");
			//tag_areainfo表内容
			String coordinateSystemId = tag.getString("coordinateSystemId");
			String coordinateSystemName = tag.getString("coordinateSystemName");
			String areaId = tag.getString("areaId");
			String areaName = tag.getString("areaName");	
			//tag_zonesinfo表内容
			JSONArray zones = tag.getJSONArray("zones");
			System.out.println(zones);
		}
	}

}

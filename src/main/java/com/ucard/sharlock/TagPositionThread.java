package com.ucard.sharlock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ucard.sharlock.tags.entity.TagAreainfo;
import com.ucard.sharlock.tags.entity.TagBaseinfo;
import com.ucard.sharlock.tags.entity.TagPosition;
import com.ucard.sharlock.tags.entity.TagZonesinfo;
import com.ucard.sharlock.tags.service.ILocationService;
import com.ucard.sharlock.tags.service.ITagAreainfoService;
import com.ucard.sharlock.tags.service.ITagBaseinfoService;
import com.ucard.sharlock.tags.service.ITagPositionService;
import com.ucard.sharlock.tags.service.ITagStatusinfoService;
import com.ucard.sharlock.tags.service.ITagZonesinfoService;


@Component
@Configurable
@EnableScheduling   // 2.开启定时任务
public class TagPositionThread{

	@Autowired
	private ILocationService locationService;
	
	@Autowired
	private ITagPositionService tagPositionService;
	
	@Autowired
	private ITagZonesinfoService tagZonesinfoService;
	
	@Autowired
	private ITagAreainfoService tagAreainfoService;
	
	@Autowired
	private ITagBaseinfoService tagBaseinfoService;
	@Autowired
	private ITagStatusinfoService tagStatusinfoService;
	
	@Scheduled(initialDelay = 1000, fixedDelay = 10000)
	public void getTagPosition() throws Exception {
		
		System.out.println("schedule getTagPosition on every 10 s");
		
		JSONObject result = locationService.getTagPosition("2", null, null, null, null, null, "true", null, null, null);
		//Timestamp responseTS = result.getTimestamp("responseTS");
		JSONArray tags = result.getJSONArray("tags");
		
		if(tags==null)
			return;
		for(int i=0;i<tags.size();i++)
		{
			JSONObject tag= (JSONObject) tags.getJSONObject(i);
			//System.out.println("时间： 11111111111111"+tag);
			String id = tag.getString("id");
			Long positionTS = tag.getLong("positionTS");
			//tag_position表内容
			JSONArray sp = tag.getJSONArray("smoothedPosition");
			double spAccuracy = tag.getDoubleValue("smoothedPositionAccuracy");
			JSONArray p = tag.getJSONArray("position");
			double pAccuracy = tag.getDoubleValue("positionAccuracy");
			JSONArray covarianceMatrix = tag.getJSONArray("covarianceMatrix");
			TagPosition tp = new TagPosition();
			tp.setTagId(id);
			tp.setTagPositionts(positionTS);
			tp.setTagSmoothedpostionx(sp.getBigDecimal(0));
			tp.setTagSmoothedpostiony(sp.getBigDecimal(1));
			tp.setTagSmoothedpostionz(sp.getBigDecimal(2));
			tp.setTagSmoothedpostionaccuracy(BigDecimal.valueOf(spAccuracy));
			tp.setTagPostionx(p.getBigDecimal(0));
			tp.setTagPostiony(p.getBigDecimal(1));
			tp.setTagPostionz(p.getBigDecimal(2));
			tp.setTagPostionaccuracy(BigDecimal.valueOf(pAccuracy));
			tp.setTagCovariancematrixxx(covarianceMatrix.getBigDecimal(0));
			tp.setTagCovariancematrixxy(covarianceMatrix.getBigDecimal(1));
			tp.setTagCovariancematrixyx(covarianceMatrix.getBigDecimal(2));
			tp.setTagCovariancematrixyy(covarianceMatrix.getBigDecimal(3));
			tagPositionService.save(tp);
			//tag_areainfo表内容
			String coordinateSystemId = tag.getString("coordinateSystemId");
			String coordinateSystemName = tag.getString("coordinateSystemName");
			String areaId = tag.getString("areaId");
			String areaName = tag.getString("areaName");
			TagAreainfo tai = new TagAreainfo();
			tai.setTagId(id);
			tai.setTagPositionts(positionTS);
			tai.setTagAreaname(areaName);
			tai.setTagCoordinatesystemid(coordinateSystemId);
			tai.setTagCoordinatesystemname(coordinateSystemName);
			tai.setTagAreaid(areaId);
			tagAreainfoService.save(tai);
			//tag_zonesinfo表内容
			JSONArray zones = tag.getJSONArray("zones");
			if(zones==null)
			{
				return;
			}
			else
			{
				for(int j=0;j<zones.size();j++)
				{
					JSONObject zone= (JSONObject) zones.getJSONObject(i);
					String ID = zone.getString("id");
					String name = zone.getString("name");
					TagZonesinfo tzi = new TagZonesinfo();
					tzi.setTagId(id);
					tzi.setTagPositionts(positionTS);
					tzi.setTagZoneid(ID);
					tzi.setTagZonename(name);
					tagZonesinfoService.save(tzi);
				}
			}
		}
	}
	
	@Scheduled(initialDelay = 5000, fixedDelay = 10000)
	public void getTaginformation() throws Exception {
		
		System.out.println("schedule getTagPosition on every 10 seconds");
		
		JSONObject result = locationService.getTagInfo("2", null, null, null, null, "true", null, null, null, null, null);
		JSONArray tags = result.getJSONArray("tags");
		
		if(tags==null)
			return;
		for(int i=0;i<tags.size();i++)
		{
			JSONObject tag= (JSONObject) tags.getJSONObject(i);
			//tagBaseinfoService
			String id = tag.getString("id");
			String color = tag.getString("color");
			String deviceType = tag.getString("deviceType");
			String group = tag.getString("group");
			String name = tag.getString("name");
			TagBaseinfo tbi = new TagBaseinfo();
			tbi.setTagColor(color);
			tbi.setTagDevicetype(deviceType);
			tbi.setTagGroup(group);
			tbi.setTagId(id);
			tbi.setTagName(name);
			tagBaseinfoService.saveOrUpdate(tbi);//TODO 重复的药删除
			//tagStatusinfoService
			
		}
		
	}
}

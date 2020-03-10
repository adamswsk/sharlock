package com.ucard.sharlock;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ucard.sharlock.tags.entity.TagAreainfo;
import com.ucard.sharlock.tags.entity.TagBaseinfo;
import com.ucard.sharlock.tags.entity.TagPosition;
import com.ucard.sharlock.tags.entity.TagStatusinfo;
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
	
	private static int num = 0;
	private static Map<Integer,String> map= new HashMap<Integer,String>();
	private static Set<String> hashset = new HashSet<>();
/*	
	@Scheduled(initialDelay = 1000, fixedDelay = 200)
	public void getTagPosition() throws Exception {
		
		System.out.println("schedule getTagPosition on every 200 ms");
		
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
			QueryWrapper<TagPosition> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("tag_id", id).eq("tag_positionTS", positionTS);
			if(tagPositionService.count(queryWrapper)==0)
			{
				tagPositionService.save(tp);
				System.out.println("1: SAVED");
			}
			else
			{
				System.out.println("1: "+id+"-"+positionTS+"has exited");
			}
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
			QueryWrapper<TagAreainfo> queryAreainfoWrapper = new QueryWrapper<>();
			queryAreainfoWrapper.eq("tag_id", id).eq("tag_positionTS", positionTS);
			if(tagAreainfoService.count(queryAreainfoWrapper)==0)
			{
				tagAreainfoService.save(tai);
				System.out.println("2: SAVED");
			}
			else
			{
				System.out.println("2: "+id+"-"+positionTS+"has exited");
			}
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
					JSONObject zone= (JSONObject) zones.getJSONObject(j);
					String ID = zone.getString("id");
					String name = zone.getString("name");
					TagZonesinfo tzi = new TagZonesinfo();
					tzi.setTagId(id);
					tzi.setTagPositionts(positionTS);
					tzi.setTagZoneid(ID);
					tzi.setTagZonename(name);
					QueryWrapper<TagZonesinfo> queryZonesinfoWrapper = new QueryWrapper<>();
					queryZonesinfoWrapper.eq("tag_id", id).eq("tag_positionTS", positionTS);
					if(tagZonesinfoService.count(queryZonesinfoWrapper)==0)
					{
						tagZonesinfoService.save(tzi);
						System.out.println("3: SAVED");
					}
					else
					{
						System.out.println("3: "+id+"-"+positionTS+"has exited");
					}
					
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
			QueryWrapper<TagBaseinfo> queryWrapper1 = new QueryWrapper<>();
			queryWrapper1.eq("tag_id", id);
			if(tagBaseinfoService.count(queryWrapper1)==0)
			{
				tagBaseinfoService.save(tbi);
				System.out.println("4: SAVED");
			}
			else
			{
				System.out.println("4: "+id+" has exited");
			}
			
			//tagStatusinfoService
			TagStatusinfo tsi = new TagStatusinfo();
			Long lastPacketTS = tag.getLong("lastPacketTS");
			double batteryVoltage = tag.getDoubleValue("batteryVoltage");
			Long batteryVoltageTS = tag.getLong("batteryVoltageTS");
			String batteryAlarm = tag.getString("batteryAlarm");
			Long batteryAlarmTS = tag.getLong("batteryAlarmTS");
			String buttonState = tag.getString("buttonState");
			Long buttonStateTS = tag.getLong("buttonStateTS");
			Long lastButtonPressTS = tag.getLong("lastButtonPressTS");
			Long lastButton2PressTS = tag.getLong("lastButton2PressTS");
			double rssi = tag.getDoubleValue("rssi");
			Long rssiTS = tag.getLong("rssiTS");
			String rssiLocator = tag.getString("rssiLocator");
			double txRate = tag.getDoubleValue("txRate");
			Long txRateTS = tag.getLong("txRateTS");
			double txPower = tag.getDoubleValue("txPower");
			Long txPowerTS = tag.getLong("txPowerTS");
			tsi.setTagBatteryalarm(batteryAlarm);
			tsi.setTagBatteryalarmts(batteryAlarmTS);
			tsi.setTagBatteryvoltage(BigDecimal.valueOf(batteryVoltage));
			tsi.setTagBatteryvoltagets(batteryVoltageTS);
			tsi.setTagButtonstate(buttonState);
			tsi.setTagButtonstatets(buttonStateTS);
			tsi.setTagId(id);
			tsi.setTagLastbutton2pressts(lastButton2PressTS);
			tsi.setTagLastbuttonpressts(lastButtonPressTS);
			tsi.setTagLastpacketts(lastPacketTS);
			tsi.setTagRssi(BigDecimal.valueOf(rssi));
			tsi.setTagRssilocator(rssiLocator);
			tsi.setTagRssits(rssiTS);
			tsi.setTagTxpower(BigDecimal.valueOf(txPower));
			tsi.setTagTxpowerts(txPowerTS);
			tsi.setTagTxrate(BigDecimal.valueOf(txRate));
			tsi.setTagTxratets(txRateTS);
			QueryWrapper<TagStatusinfo> queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("tag_id", id).eq("tag_lastPacketTS", lastPacketTS);
			if(tagStatusinfoService.count(queryWrapper2)==0)
			{
				tagStatusinfoService.save(tsi);
				System.out.println("5: SAVED");
			}
			else
			{
				System.out.println("5: "+id+"-"+lastPacketTS+"has exited");
			}
			
		}
		
	}
*/	
/*	
	//测试接口
	@Scheduled(initialDelay = 1000, fixedDelay = 5000)
	public void sendTaginformation() throws Exception {
		System.out.println("schedule sendTaginformation on every 500 ms");
		String data = String.format("%08x", num);
		String formatdata = "0xFF 0x5D 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x00 "+"0x"+data.substring(0, 2)+" 0x"+data.substring(2, 4)+" 0x"+data.substring(4, 6)+" 0x"+data.substring(6, 8);
		num++;
		System.out.println("已发送指令条数： "+num);
		System.out.println("发送指令： "+formatdata);
		JSONObject result = locationService.sendQuuppaRequest("e2f616644c6e", formatdata, "true",null);
		String status = result.getString("status");
		if(status.equalsIgnoreCase("Ok"))//发送成功
		{
		}
		else
		{
			System.out.println("sendTaginformation failed");
		}
	}
	
	@Scheduled(initialDelay = 1000, fixedDelay = 250)
	public void receiveTaginformation() throws Exception {
		System.out.println("schedule sendTaginformation on every 250ms");
		//接收payLoad
		JSONObject result2 = locationService.getTagPayloadData("e2f616644c6e", "true");
		JSONArray tags_payload = result2.getJSONArray("tags");
		for(int l=0;l<tags_payload.size();l++)
		{
			JSONObject tag_payload= (JSONObject) tags_payload.getJSONObject(l);
			JSONArray payloadData = tag_payload.getJSONArray("payloadData");
			if(payloadData==null)
			{
				continue;
			}
			for(int m=0;m<payloadData.size();m++)
			{
				
				if(payloadData.getJSONObject(m)==null)
				{
					continue;
				}
				if(!hashset.contains(payloadData.getJSONObject(m).getString("data")))
				{
					hashset.add(payloadData.getJSONObject(m).getString("data"));
				}
				
				System.out.println(payloadData.getJSONObject(m));
			}
			
		}
		//System.out.println("map size : "+map.size());
		System.out.println("hashSet size : "+hashset.size());
		
		if(num>=26)
		{
			System.out.println("***********************************************************");
			
			for (String s:hashset) {
				System.out.println(s);
			}
			
		}
	}*/
}

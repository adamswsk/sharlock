package com.ucard.sharlock.tags.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ucard.sharlock.tags.entity.Categorizationinfo;
import com.ucard.sharlock.tags.entity.TagAreainfo;
import com.ucard.sharlock.tags.entity.TagBaseinfo;
import com.ucard.sharlock.tags.entity.TagPosition;
import com.ucard.sharlock.tags.entity.TagStatusinfo;
import com.ucard.sharlock.tags.entity.TagZonesinfo;
import com.ucard.sharlock.tags.entity.Users;
import com.ucard.sharlock.tags.service.ICategorizationinfoService;
import com.ucard.sharlock.tags.service.ILocationService;
import com.ucard.sharlock.tags.service.ITagAreainfoService;
import com.ucard.sharlock.tags.service.ITagBaseinfoService;
import com.ucard.sharlock.tags.service.ITagPositionService;
import com.ucard.sharlock.tags.service.ITagStatusinfoService;
import com.ucard.sharlock.tags.service.ITagZonesinfoService;
import com.ucard.sharlock.tags.service.IUsersService;

/**
 * <p>
 *  api前端控制器
 * </p>
 *
 * @author wusk
 * @since 2020-02-25
 */
@RestController
@ResponseBody
@RequestMapping(value = "/frontend",produces = "application/json;charset=UTF-8")
public class TagAPI4FrontEndController {
	
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
	@Autowired
	private IUsersService userService;
	@Autowired
	private ICategorizationinfoService categorizationinfoService;
	
	@GetMapping(value="/getTagInfo")
	public JSONObject getTagInfo()
	{
		JSONObject result = locationService.getTagInfo("2", null, null, null, null, "true", null, null, null, null, null);
		JSONArray tags = result.getJSONArray("tags");
		
		if(tags==null)
			return null;
		for(int i=0;i<tags.size();i++)
		{
			JSONObject tag= (JSONObject) tags.getJSONObject(i);
			//tagBaseinfoService
			String id = tag.getString("id");
			if(id==null || id=="")
			{
				continue;
			}
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
				//System.out.println("4: SAVED");
			}
			else
			{
				tagBaseinfoService.update(tbi, queryWrapper1);
				//System.out.println("4: "+id+" have been updated");
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
				//System.out.println("5: SAVED");
			}
			else
			{
				//System.out.println("5: "+id+"-"+lastPacketTS+"has exited");
			}
			
		}
		return result;
	}
	
	@GetMapping(value="/getTagPosition")
	public JSONObject getTagPosition()
	{
		JSONObject result = locationService.getTagPosition("2", null, null, null, null, null, "true", null, null, null);
		//Timestamp responseTS = result.getTimestamp("responseTS");
		JSONArray tags = result.getJSONArray("tags");
		
		if(tags==null)
			return null;
		for(int i=0;i<tags.size();i++)
		{
			JSONObject tag= (JSONObject) tags.getJSONObject(i);
			//System.out.println("时间： 11111111111111"+tag);
			String id = tag.getString("id");
			Long positionTS = tag.getLong("positionTS");
			if(id==null || id=="")
			{
				continue;
			}
			
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
				//System.out.println("1: SAVED");
			}
			else
			{
				//System.out.println("1: "+id+"-"+positionTS+"has exited");
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
				//System.out.println("2: SAVED");
			}
			else
			{
				//System.out.println("2: "+id+"-"+positionTS+"has exited");
			}
			//tag_zonesinfo表内容
			JSONArray zones = tag.getJSONArray("zones");
			if(zones==null)
			{
				return null;
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
						//System.out.println("3: SAVED");
					}
					else
					{
						//System.out.println("3: "+id+"-"+positionTS+"has exited");
					}
					
				}
			}
		}
		return result;
	}
	@GetMapping(value="/getTagBaseInfo")
	public List<TagBaseinfo> getTagBaseInfo(
			@RequestParam(value = "tagid",required = false) String tagid,
			@RequestParam(value = "name",required = false) String name)
	{
		if(tagid!=null)
		{
			return tagBaseinfoService.findinfobytagid(tagid);
		}else
		{
			return tagBaseinfoService.findinfobyname(name);
		}
	}
	
	@GetMapping(value="/getTagPositionTrail")
	public List<TagPosition> getTagPositionTrail(
			@RequestParam(value = "tagid",required = true) String tagid,
			@RequestParam(value = "BeginPositionTS",required = false) Long BeginPositionTS,
			@RequestParam(value = "EndPositionTS",required = false) Long EndPositionTS)
	{
		if(BeginPositionTS != null && BeginPositionTS != 0 && EndPositionTS != null && EndPositionTS != 0)
		{
			return tagPositionService.findposbyidandtime(tagid, BeginPositionTS,EndPositionTS);
		}
		else
		{
			return tagPositionService.findpositionbytagid(tagid);
		}
	}
	
	@GetMapping(value="/bindTagAndUser")
	public boolean bindTagAndUser(
			@RequestParam(value = "tagid",required = false) String tagid,
			@RequestParam(value = "UserName",required = true) String UserName,
			@RequestParam(value = "UserID",required = true) String UserID,
			@RequestParam(value = "UserPost",required = false) String UserPost)
	{
		Users entity = new Users();
		entity.setTagId(tagid);
		entity.setUserName(UserName);
		entity.setUserId(UserID);
		entity.setUserPost(UserPost);
		
		return userService.save(entity);
	}
	
	@GetMapping(value="/getPostType")
	public List<Categorizationinfo> getPostType()
	{		
		return categorizationinfoService.getpostlist();
	}
	
	@GetMapping(value="/configNewTag")
	public boolean configNewTag()
	{	
		//查找users表，看哪些标签未设置
		QueryWrapper<Users> queryUserWrapper = new QueryWrapper<>();
		queryUserWrapper.eq("station", 0);
		List<Users> list_users = userService.list(queryUserWrapper);
		for(Users user:list_users)
		{
			//获取标签ID、姓名、岗位
			if(user.getTagId()!=null && user.getTagId()!="")
			{
				String post = user.getUserPost();
				String name = user.getUserName();
				String tagid = user.getTagId();
				//根据岗位查找Group和Color信息
				QueryWrapper<Categorizationinfo> queryCategorizationinfoWrapper = new QueryWrapper<>();
				queryCategorizationinfoWrapper.eq("user_post", post);
				Categorizationinfo info = categorizationinfoService.getOne(queryCategorizationinfoWrapper);
				String group = info.getTagGroup();
				String color = info.getTagColor();
				String config = info.getTagConfig();
				//设置标签，成功后将users表更新
				locationService.setTagGroup(null, group, tagid, null, null, null);
				locationService.configureTag(null, config, tagid, null, null, "230");
				locationService.modifyTag(name, null, tagid, color, null);
			}
		}
		
		return true;
	}
	
	@GetMapping(value="/refreshTagConfig")
	public String refreshTagConfig()
	{	
		int counter = 0;
		//查找所有标注是0的标签
		//查找users表，看哪些标签未设置
		QueryWrapper<Users> queryUserWrapper = new QueryWrapper<>();
		queryUserWrapper.eq("station", 0);
		List<Users> list_users = userService.list(queryUserWrapper);
		for(Users user:list_users)
		{
			//检查这些标签是否已经配置
			String tagid = user.getTagId();
			JSONObject result = locationService.getTagInfo("2", tagid, null, null, null, "true", null, null, null, null, null);
			JSONArray tags = result.getJSONArray("tags");
			if(tags!=null)
			{
				JSONObject tag= (JSONObject) tags.getJSONObject(0);
				if(tag.getString("color")!=null && tag.getString("group")!=null)
				{
					//已经配置
					queryUserWrapper.eq("tag_id", tagid);
					user.setStation(true);
					userService.update(user, queryUserWrapper);
					counter++;
				}
			}
		}
		
		//导出配置文件
		locationService.exportTags(null, null, "ucardTags", null);
		return "需要配置标签数： "+list_users.size()+" 已配置标签： "+counter;
	}
	

}

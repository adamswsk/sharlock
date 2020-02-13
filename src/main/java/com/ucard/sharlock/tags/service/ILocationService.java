/**
 * 第三方接口调用：CombineTags
 * @author wusk
 * 
 */
package com.ucard.sharlock.tags.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

@FeignClient(url = "${decisionEngine.url}",name="engine")
public interface ILocationService {

	@RequestMapping(value="/qpe/combineTags",method= RequestMethod.POST)
	public JSONObject combineTags(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "tag",required = true) String tag,
			@RequestParam(value = "name",required = false) String name);
	
	@RequestMapping(value="/qpe/commandTag",method= RequestMethod.POST)
	public JSONObject commandTag(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "id",required = false) String id,
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "time",required = false) String time);
	
	@RequestMapping(value="/qpe/configureTag",method= RequestMethod.POST)
	public JSONObject configureTag(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "id",required = false) String id,
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "time",required = false) String time,
			@RequestParam(value = "channel",required = true) String channel);
	
	@RequestMapping(value="/qpe/exportTags",method= RequestMethod.POST)
	public JSONObject exportTags(
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "filename",required = false) String filename,
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/getLocatorInfo",method= RequestMethod.POST)
	public JSONObject getLocatorInfo(
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/getPEInfo",method= RequestMethod.POST)
	public JSONObject getPEInfo(
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/getProjectInfo",method= RequestMethod.POST)
	public JSONObject getProjectInfo(
			@RequestParam(value = "version",required = true) String version,
			@RequestParam(value = "noImageBytes",required = false) String noImageBytes);
	
	@RequestMapping(value="/qpe/getTagInfo",method= RequestMethod.POST)
	public JSONObject getTagInfo(
			@RequestParam(value = "version",required = true) String version,
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "maxAge",required = false) String maxAge,
			@RequestParam(value = "id",required = false) String id,
			@RequestParam(value = "humanReadable",required = false) String humanReadable,
			@RequestParam(value = "coord",required = false) String coord,
			@RequestParam(value = "zone",required = false) String zone,
			@RequestParam(value = "ignoreUnknownTags",required = false) String ignoreUnknownTags,
			@RequestParam(value = "rssi",required = false) String rssi,
			@RequestParam(value = "locator",required = false) String locator);
	
	@RequestMapping(value="/qpe/getTagPayloadData",method= RequestMethod.POST)
	public JSONObject getTagPayloadData(
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/getTagPosition",method= RequestMethod.POST)
	public JSONObject getTagPosition(
			@RequestParam(value = "version",required = true) String version,
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "maxAge",required = false) String maxAge,
			@RequestParam(value = "id",required = false) String id,
			@RequestParam(value = "radius",required = false) String radius,
			@RequestParam(value = "humanReadable",required = false) String humanReadable,
			@RequestParam(value = "coord",required = false) String coord,
			@RequestParam(value = "zone",required = false) String zone,
			@RequestParam(value = "ignoreUnknownTags",required = false) String ignoreUnknownTags);
	
	@RequestMapping(value="/qpe/getQuuppaRequestResponse",method= RequestMethod.POST)
	public JSONObject getQuuppaRequestResponse(
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/importTags",method= RequestMethod.POST)
	public JSONObject importTags(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "filename",required = true) String filename,
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/logBinaryData",method= RequestMethod.POST)
	public JSONObject logBinaryData(
			@RequestParam(value = "folder",required = false) String folder,
			@RequestParam(value = "prefix",required = false) String prefix,
			@RequestParam(value = "time",required = false) String time);
	
	@RequestMapping(value="/qpe/logging",method= RequestMethod.POST)
	public JSONObject logging(
			@RequestParam(value = "mode",required = true) String mode,
			@RequestParam(value = "id",required = false) String id,
			@RequestParam(value = "name",required = false) String name,
			@RequestParam(value = "type",required = true) String type,
			@RequestParam(value = "ip",required = true) String ip,
			@RequestParam(value = "port",required = true) String port,
			@RequestParam(value = "interval",required = true) String interval,
			@RequestParam(value = "delay",required = false) String delay,
			@RequestParam(value = "folder",required = true) String folder,
			@RequestParam(value = "prefix",required = false) String prefix,
			@RequestParam(value = "lines",required = true) String lines,
			@RequestParam(value = "time",required = false) String time);
	
	@RequestMapping(value="/qpe/modifyTag",method= RequestMethod.POST)
	public JSONObject modifyTag(
			@RequestParam(value = "name",required = false) String name,
			@RequestParam(value = "note",required = false) String note,
			@RequestParam(value = "tag",required = true) String tag,
			@RequestParam(value = "color",required = false) String color,
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/overrideLicenseMode",method= RequestMethod.POST)
	public JSONObject overrideLicenseMode(
			@RequestParam(value = "mode",required = true) String mode,
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/sendQuuppaRequest",method= RequestMethod.POST)
	public JSONObject sendQuuppaRequest(
			@RequestParam(value = "tag",required = true) String tag,
			@RequestParam(value = "requestData",required = true) String requestData,
			@RequestParam(value = "humanReadable",required = false) String humanReadable,
			@RequestParam(value = "time",required = false) String time);
	
	@RequestMapping(value="/qpe/setLocatorMode",method= RequestMethod.POST)
	public JSONObject setLocatorMode(
			@RequestParam(value = "mode",required = true) String mode,
			@RequestParam(value = "locator",required = true) String locator,
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/setQPEMode",method= RequestMethod.POST)
	public JSONObject setQPEMode(
			@RequestParam(value = "mode",required = true) String mode,
			@RequestParam(value = "humanReadable",required = false) String humanReadable);
	
	@RequestMapping(value="/qpe/setTagGroup",method= RequestMethod.POST)
	public JSONObject setTagGroup(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "id",required = true) String id,
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "humanReadable",required = false) String humanReadable,
			@RequestParam(value = "createNew",required = false) String createNew);
	
	@RequestMapping(value="/qpe/upgradeAllFW",method= RequestMethod.POST)
	public JSONObject upgradeAllFW(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "port",required = true) String port,
			@RequestParam(value = "maxnum",required = false) String maxnum,
			@RequestParam(value = "coord",required = false) String coord,
			@RequestParam(value = "locator",required = false) String locator);
}

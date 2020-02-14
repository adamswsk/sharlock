package com.ucard.sharlock.tags.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ucard.sharlock.tags.service.ILocationService;


/**
 * <p>
 *  api前端控制器
 * </p>
 *
 * @author wusk
 * @since 2020-02-14
 */
@RestController
@RequestMapping("/apis")
public class TagAPIController {
	
	@Autowired
	ILocationService locationService;
	
	@GetMapping(value="/combineTags")
	public JSONObject combineTags(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "tag",required = true) String tag,
			@RequestParam(value = "name",required = false) String name)
	{
		return locationService.combineTags(mode, tag, name);
	}
	
	@GetMapping(value="/getPEInfo")
	public JSONObject getPEInfo(
			@RequestParam(value = "humanReadable",required = false) String humanReadable)
	{
		return locationService.getPEInfo(humanReadable);
	}
	
	@GetMapping(value="/commandTag")
	public JSONObject commandTag(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "id",required = false) String id,
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "time",required = false) String time)
	{
		return locationService.commandTag(mode, id, tag, group, time);
	}
	
	@GetMapping(value="/configureTag")
	public JSONObject configureTag(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "id",required = false) String id,
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "time",required = false) String time,
			@RequestParam(value = "channel",required = true) String channel)
	{
		return locationService.configureTag(mode, id, tag, group, time, channel);
	}
	
	@GetMapping(value="/getLocatorInfo")
	public JSONObject getLocatorInfo(
			@RequestParam(value = "humanReadable",required = false) String humanReadable)
	{
		return locationService.getLocatorInfo(humanReadable);
	}
	
	@GetMapping(value="/getProjectInfo")
	public JSONObject getProjectInfo(
			@RequestParam(value = "version",required = true) String version,
			@RequestParam(value = "noImageBytes",required = false) String noImageBytes)
	{
		return locationService.getProjectInfo(version, noImageBytes);
	}
	
	@GetMapping(value="/getUcardRequestResponse")
	public JSONObject getQuuppaRequestResponse(
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "humanReadable",required = false) String humanReadable)
	{
		return locationService.getQuuppaRequestResponse(tag, humanReadable);
	}
	
	@GetMapping(value="/getTagInfo")
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
			@RequestParam(value = "locator",required = false) String locator)
	{
		return locationService.getTagInfo(version, tag, group, maxAge, id, humanReadable, coord, zone, ignoreUnknownTags, rssi, locator);
	}
	
	@GetMapping(value="/getTagPayloadData")
	public JSONObject getTagPayloadData(
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "humanReadable",required = false) String humanReadable)
	{
		return locationService.getTagPayloadData(tag, humanReadable);
	}
	
	@GetMapping(value="/getTagPosition")
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
			@RequestParam(value = "ignoreUnknownTags",required = false) String ignoreUnknownTags)
	{
		return locationService.getTagPosition(version, tag, group, maxAge, id, radius, humanReadable, coord, zone, ignoreUnknownTags);
	}
	
	@GetMapping(value="/importTags")
	public JSONObject importTags(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "filename",required = true) String filename,
			@RequestParam(value = "humanReadable",required = false) String humanReadable)
	{
		return locationService.importTags(mode, filename, humanReadable);
	}
	
	@GetMapping(value="/logBinaryData")
	public JSONObject logBinaryData(
			@RequestParam(value = "folder",required = false) String folder,
			@RequestParam(value = "prefix",required = false) String prefix,
			@RequestParam(value = "time",required = false) String time)
	{
		return locationService.logBinaryData(folder, prefix, time);
	}
	
	@GetMapping(value="/logging")
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
			@RequestParam(value = "time",required = false) String time)
	{
		return locationService.logging(mode, id, name, type, ip, port, interval, delay, folder, prefix, lines, time);
	}
	
	@GetMapping(value="/modifyTag")
	public JSONObject modifyTag(
			@RequestParam(value = "name",required = false) String name,
			@RequestParam(value = "note",required = false) String note,
			@RequestParam(value = "tag",required = true) String tag,
			@RequestParam(value = "color",required = false) String color,
			@RequestParam(value = "humanReadable",required = false) String humanReadable)
	{
		return locationService.modifyTag(name, note, tag, color, humanReadable);
	}
	
	@GetMapping(value="/overrideLicenseMode")
	public JSONObject overrideLicenseMode(
			@RequestParam(value = "mode",required = true) String mode,
			@RequestParam(value = "humanReadable",required = false) String humanReadable)
	{
		return locationService.overrideLicenseMode(mode, humanReadable);
	}
	
	@GetMapping(value="/sendUcardRequest")
	public JSONObject sendQuuppaRequest(
			@RequestParam(value = "tag",required = true) String tag,
			@RequestParam(value = "requestData",required = true) String requestData,
			@RequestParam(value = "humanReadable",required = false) String humanReadable,
			@RequestParam(value = "time",required = false) String time)
	{
		return locationService.sendQuuppaRequest(tag, requestData, humanReadable, time);
	}
	
	@GetMapping(value="/setLocatorMode")
	public JSONObject setLocatorMode(
			@RequestParam(value = "mode",required = true) String mode,
			@RequestParam(value = "locator",required = true) String locator,
			@RequestParam(value = "humanReadable",required = false) String humanReadable)
	{
		return locationService.setLocatorMode(mode, locator, humanReadable);
	}
	
	@GetMapping(value="/setQPEMode")
	public JSONObject setQPEMode(
			@RequestParam(value = "mode",required = true) String mode,
			@RequestParam(value = "humanReadable",required = false) String humanReadable)
	{
		return locationService.setQPEMode(mode, humanReadable);
	}
	
	@GetMapping(value="/setTagGroup")
	public JSONObject setTagGroup(
			@RequestParam(value = "mode",required = false) String mode,
			@RequestParam(value = "id",required = true) String id,
			@RequestParam(value = "tag",required = false) String tag,
			@RequestParam(value = "group",required = false) String group,
			@RequestParam(value = "humanReadable",required = false) String humanReadable,
			@RequestParam(value = "createNew",required = false) String createNew)
	{
		return locationService.setTagGroup(mode, id, tag, group, humanReadable, createNew);
	}	

}

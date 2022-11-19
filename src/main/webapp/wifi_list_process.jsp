<%@page import="com.dao.TbPublicWifiInfoDAO"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.google.gson.JsonArray"%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.vo.TbPublicWifiInfoVO"%>
<%@page import="com.service.TbPublicWifiInfoService"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%
	request.setCharacterEncoding("utf-8");

	Double lat1 = Double.parseDouble(request.getParameter("lat1"));
	Double lnt1 = Double.parseDouble(request.getParameter("lnt1"));

	TbPublicWifiInfoService service = new TbPublicWifiInfoService();
	
	// wifi 조회 Log 저장
	service.wifiLogInsert(lat1, lnt1);
	
	// wifi 조회 list
	List<TbPublicWifiInfoVO> list = service.wifiList(lat1, lnt1);
	
	Gson gson = new Gson();
	JsonObject result = new JsonObject();
	JsonArray arr = new JsonArray();
	
	for(TbPublicWifiInfoVO vo : list) {
		JsonObject obj = new JsonObject();
		obj.addProperty("X_SWIFI_MGR_NO", vo.getX_SWIFI_MGR_NO());
		obj.addProperty("X_SWIFI_WRDOFC", vo.getX_SWIFI_WRDOFC());
		obj.addProperty("X_SWIFI_MAIN_NM", vo.getX_SWIFI_MAIN_NM());
		obj.addProperty("X_SWIFI_ADRES1", vo.getX_SWIFI_ADRES1());
		obj.addProperty("X_SWIFI_ADRES2", vo.getX_SWIFI_ADRES2());
		obj.addProperty("X_SWIFI_INSTL_FLOOR", vo.getX_SWIFI_INSTL_FLOOR());
		obj.addProperty("X_SWIFI_INSTL_TY", vo.getX_SWIFI_INSTL_TY());
		obj.addProperty("X_SWIFI_INSTL_MBY", vo.getX_SWIFI_INSTL_MBY());
		obj.addProperty("X_SWIFI_SVC_SE", vo.getX_SWIFI_SVC_SE());
		obj.addProperty("X_SWIFI_CMCWR", vo.getX_SWIFI_CMCWR());
		obj.addProperty("X_SWIFI_CNSTC_YEAR", vo.getX_SWIFI_CNSTC_YEAR());
		obj.addProperty("X_SWIFI_INOUT_DOOR", vo.getX_SWIFI_INOUT_DOOR());
		obj.addProperty("X_SWIFI_REMARS3", vo.getX_SWIFI_REMARS3());
		obj.addProperty("LAT", vo.getLAT());
		obj.addProperty("LNT", vo.getLNT());
		obj.addProperty("WORK_DTTM", vo.getWORK_DTTM());
		obj.addProperty("distance", vo.getDistance());
		obj.addProperty("rowNum", vo.getRowNum());
		
		arr.add(obj);
	}
	
	result.add("list", arr);
	//System.out.println(result);
	out.write(gson.toJson(result));
	
%>
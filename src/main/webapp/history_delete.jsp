<%@page import="com.vo.WifiLogVO"%>
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

	int id = Integer.parseInt(request.getParameter("id"));
	
	TbPublicWifiInfoService service = new TbPublicWifiInfoService();
	boolean result = service.wifiLogDelete(id);
	
	if(result){
		response.sendRedirect("history_list.jsp");
	}
%>


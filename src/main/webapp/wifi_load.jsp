<%@page import="com.vo.TbPublicWifiInfoVO"%>
<%@page import="com.service.TbPublicWifiInfoService"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/css/wifi.css" >
</head>
<body>
<%
 TbPublicWifiInfoService infoService = new TbPublicWifiInfoService();
 boolean result = infoService.wifiInfo();
 int total = infoService.wifiTotal();

%>
<h2>와이파이 정보 구하기</h2>

<div>
	<ul>
		<li><a href="index.jsp">홈</a></li>
		<li><a href="history_list.jsp">위치 히스트리 목록</a></li>
		<li><a href="wifi_load.jsp">Open API 와이파이 정보 가져오기</a></li>
	</ul>
</div>


<div id="wifiLoad">
	
	<%
	if(result){ 
	%>
		<span id="wifi_font"><%=total %>개의 WIFI 정보를 정상적으로 저장하였습니다.</span></br>
		<span><a href="index.jsp">홈으로가기</a></span>
	
	<%}else{ %>
	
		<span id="wifi_font"><%=total %>개의 WIFI 정보가 이미 저장되어 있습니다.</span></br>
		<span><a href="index.jsp">홈으로가기</a></span>
 	<%
	 }
	%>
</div>

</body>
</html>
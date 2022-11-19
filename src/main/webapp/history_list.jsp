<%@page import="com.vo.WifiLogVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
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
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>
<%
	TbPublicWifiInfoService service = new TbPublicWifiInfoService();
	List<WifiLogVO> list = service.wifiLogList();
%>
<h2>와이파이 정보 구하기</h2>

<div>
	<ul>
		<li><a href="index.jsp">홈</a></li>
		<li><a href="history_list.jsp">위치 히스트리 목록</a></li>
		<li><a href="wifi_load.jsp">Open API 와이파이 정보 가져오기</a></li>
	</ul>
</div>


<table>
	<tr>
		<th>ID</th>
		<th>X좌표</th>
		<th>Y좌표</th>
		<th>조회일자</th>
		<th>비고</th>
	</tr>

	<% for(WifiLogVO vo : list) { %>	
		<%if(("N").equals(vo.getSTATUS())) { %>
			<tr>
				<td><%= vo.getID() %></td>
				<td><%= vo.getLAT() %></td>
				<td><%= vo.getLNT() %></td>
				<td><%= vo.getREG_DATE() %></td>
				<td><a href = "history_delete.jsp?id=<%=vo.getID() %>"><button>삭제</button></a></td>
			</tr>
		<%} %>
	<%} %>
</table>

</body>
</html>
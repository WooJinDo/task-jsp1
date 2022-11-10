<%@page import="com.service.ApiExplorerService"%>
<%@ page import="java.io.InputStreamReader"%>
<%@ page import="java.net.HttpURLConnection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
ApiExplorerService apiExplorerService = new ApiExplorerService();
 apiExplorerService.test();
%>
<h2>와이파이 정보 구하기</h2>

<ul>
	<li>홈</li>
	<li>위치 히스트뢰 목록</li>
	<li>Open API 와이파이 정보 가져오기</li>
</ul>

<label>LAT : 
<input type="text" />
</label>

<label>LNT 
<input type="text" />
</label>

<input type="submit" value="내 위치 가져오기" />
<input type="submit" value="근처 WIPI 정보 보기" />

<table>
	<tr>
		<td>거리(Km)</td>
		<td>관리번호</td>
		<td>자치구</td>
		<td>와이파이명</td>
		<td>도로명주소</td>
		<td>상세주소</td>
		<td>설치위치(층)</td>
		<td>설치유형</td>
		<td>설치기관</td>
		<td>서비스구분</td>
		<td>망종류</td>
		<td>설치년도</td>
		<td>실내외구분</td>
		<td>WIFI접속환경</td>
		<td>X좌표</td>
		<td>Y좌표</td>
		<td>작업일자</td>
	</tr>
	
	
	<tr>
		<td colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
	</tr>
</table>

</body>
</html>
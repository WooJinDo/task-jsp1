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

<script>
$(function() {
	
	$("#location").click(function(){
		let lat = 37.5544069;
		let lnt = 126.8998666;
	    
		$("#LAT").val(lat);
		$("#LNT").val(lnt);
		
	});
	
	$("#near").click(function(){
		/* if(lat1 == null){
			alert("test");
		} */
		
		let lat1 = $("#LAT").val();
		let lnt1 = $("#LNT").val();
		
		$.ajax({
			type : "POST",
			url : "wifi_list_process.jsp",
			dataType: "JSON",
			data: { lat1 : lat1, lnt1 : lnt1 },
			success:function(data){
				
				$("table tr#info").remove();
				for(i = 0; i < data.list.length; i++){
					let str = "<tr id='info'>"
					+ "<td>" + data.list[i].rowNum + "</td>"
					+ "<td>" + data.list[i].distance + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_MGR_NO + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_WRDOFC + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_MAIN_NM + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_ADRES1 + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_ADRES2 + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_INSTL_FLOOR + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_INSTL_TY + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_INSTL_MBY + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_SVC_SE + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_CMCWR + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_CNSTC_YEAR + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_INOUT_DOOR + "</td>"
					+ "<td>" + data.list[i].X_SWIFI_REMARS3 + "</td>"
					+ "<td>" + data.list[i].LAT + "</td>"
					+ "<td>" + data.list[i].LNT + "</td>"
					+ "<td>" + data.list[i].WORK_DTTM + "</td>"
					+ "</tr>"
					$("table").append(str);
				}
			},
			error: function (xhr) {
				alert("좌표 값이 존재하지 않아 데이터가 출력되지 않습니다.\n상태 : " + xhr.status);
			}
			
		});
		
	});
	
});

</script>


</head>
<body>
<h2>와이파이 정보 구하기</h2>

<div>
	<ul>
		<li><a href="index.jsp">홈</a></li>
		<li><a href="history_list.jsp">위치 히스트리 목록</a></li>
		<li><a href="wifi_load.jsp">Open API 와이파이 정보 가져오기</a></li>
	</ul>
</div>

<div>
	<label>LAT : 
	<input type="text" id="LAT"  />
	</label>
	
	<label>LNT 
	<input type="text" id="LNT" />
	</label>
	
	<button type="button" id="location" >내 위치 가져오기</button>
	<button type="button" id="near">근처 WIPI 정보 보기</button>
</div>	

<table>
	<tr>
		<th>번호(No)</th>
		<th>거리(Km)</th>
		<th>관리번호</th>
		<th>자치구</th>
		<th>와이파이명</th>
		<th>도로명주소</th>
		<th>상세주소</th>
		<th>설치위치(층)</th>
		<th>설치유형</th>
		<th>설치기관</th>
		<th>서비스구분</th>
		<th>망종류</th>
		<th>설치년도</th>
		<th>실내외구분</th>
		<th>WIFI접속환경</th>
		<th>X좌표</th>
		<th>Y좌표</th>
		<th>작업일자</th>
	</tr>
	
	<tr id="info">
		<td colspan="18">위치 정보를 입력한 후에 조회해 주세요.</td>
	</tr>
	
</table>


</body>
</html>
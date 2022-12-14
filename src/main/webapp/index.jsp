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
				alert("?????? ?????? ???????????? ?????? ???????????? ???????????? ????????????.\n?????? : " + xhr.status);
			}
			
		});
		
	});
	
});

</script>


</head>
<body>
<h2>???????????? ?????? ?????????</h2>

<div>
	<ul>
		<li><a href="index.jsp">???</a></li>
		<li><a href="history_list.jsp">?????? ???????????? ??????</a></li>
		<li><a href="wifi_load.jsp">Open API ???????????? ?????? ????????????</a></li>
	</ul>
</div>

<div>
	<label>LAT : 
	<input type="text" id="LAT"  />
	</label>
	
	<label>LNT 
	<input type="text" id="LNT" />
	</label>
	
	<button type="button" id="location" >??? ?????? ????????????</button>
	<button type="button" id="near">?????? WIPI ?????? ??????</button>
</div>	

<table>
	<tr>
		<th>??????(No)</th>
		<th>??????(Km)</th>
		<th>????????????</th>
		<th>?????????</th>
		<th>???????????????</th>
		<th>???????????????</th>
		<th>????????????</th>
		<th>????????????(???)</th>
		<th>????????????</th>
		<th>????????????</th>
		<th>???????????????</th>
		<th>?????????</th>
		<th>????????????</th>
		<th>???????????????</th>
		<th>WIFI????????????</th>
		<th>X??????</th>
		<th>Y??????</th>
		<th>????????????</th>
	</tr>
	
	<tr id="info">
		<td colspan="18">?????? ????????? ????????? ?????? ????????? ?????????.</td>
	</tr>
	
</table>


</body>
</html>
package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.dao.TbPublicWifiInfoDAO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vo.TbPublicWifiInfoVO;
import com.vo.WifiLogVO;

public class TbPublicWifiInfoService {

	int start = 1;
	int end = 1000;
	int count = 0;
	
	//wifi API 호출
	public boolean wifiInfo() {
		boolean result = false;
		
		do {
			try {
				StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
				urlBuilder.append("/" +  URLEncoder.encode("524a6f46476568713731794941684c","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
				urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
				urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
				urlBuilder.append("/" + URLEncoder.encode(String.valueOf(start),"UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
				urlBuilder.append("/" + URLEncoder.encode(String.valueOf(end),"UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
				// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
				
				// 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
				urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8")); /* 서비스별 추가 요청인자들*/
				
				URL url = new URL(urlBuilder.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-type", "application/xml");
				System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
				BufferedReader rd;
		
				// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
				if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
						rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
						rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = rd.readLine()) != null) {
						sb.append(line);
				}
				
				rd.close();
				conn.disconnect();
				
				 // JSON parser 만들어 문자열 데이터를 객체화한다.
				JsonParser parser = new JsonParser();
		        JsonObject obj = (JsonObject)parser.parse(sb.toString());
		        JsonObject tbPublicWifiInfo = (JsonObject)obj.get("TbPublicWifiInfo");
		        
		        // api에 저장되어 있는 전체 개수
		        if(count == 0) {
		        	count = Integer.parseInt(tbPublicWifiInfo.get("list_total_count").toString());
		        }
		        
		      
		        // DB에 저장되어 있는 total 개수 
		        int totalCount = wifiTotal();
		        System.out.println("totalCount = " + totalCount);
		        System.out.println("count = " + count);
		        
		        // 배열형태로
		        JsonArray parse_listArr = (JsonArray)tbPublicWifiInfo.getAsJsonArray("row");
		        
		        // 이미 DB에 n건의 데이터가 저장되어 있고, api 데이터와 동일하다면 break
		        // 동일하지 않다면 데이터 전체 삭제 후 insert
		        if(totalCount == count ) {
		        	System.out.println("데이터가 동일합니다.");
		        	break;
		        }else if(totalCount != count && end == parse_listArr.size()) {
		        	if(totalCount > 0) {
		        		System.out.println("데이터가 맞지 않아 삭제됩니다.");
			        	wifiDelete();	
		        	}
		        }
		        
		        start += 1000;	//시작 index
		        end += 1000;	//마지막 index
		       
		        // 객체형태 -> 자료형변환 insert하기
				for (int i = 0; i < parse_listArr.size(); i++) {
					JsonObject wifi = (JsonObject)parse_listArr.get(i);
					
					String X_SWIFI_MGR_NO = wifi.get("X_SWIFI_MGR_NO").getAsString();
					String X_SWIFI_WRDOFC = wifi.get("X_SWIFI_WRDOFC").getAsString(); 
					String X_SWIFI_MAIN_NM = wifi.get("X_SWIFI_MAIN_NM").getAsString();
					String X_SWIFI_ADRES1 = wifi.get("X_SWIFI_ADRES1").getAsString();
					String X_SWIFI_ADRES2 = wifi.get("X_SWIFI_ADRES2").getAsString();
					String X_SWIFI_INSTL_FLOOR = wifi.get("X_SWIFI_INSTL_FLOOR").getAsString();
					String X_SWIFI_INSTL_TY = wifi.get("X_SWIFI_INSTL_TY").getAsString();
					String X_SWIFI_INSTL_MBY = wifi.get("X_SWIFI_INSTL_MBY").getAsString();
					String X_SWIFI_SVC_SE = wifi.get("X_SWIFI_SVC_SE").getAsString();
					String X_SWIFI_CMCWR = wifi.get("X_SWIFI_CMCWR").getAsString();
					String X_SWIFI_CNSTC_YEAR = wifi.get("X_SWIFI_CNSTC_YEAR").getAsString();
					String X_SWIFI_INOUT_DOOR = wifi.get("X_SWIFI_INOUT_DOOR").getAsString();
					String X_SWIFI_REMARS3 = wifi.get("X_SWIFI_REMARS3").getAsString();
					Double LAT = wifi.get("LAT").getAsDouble();
					Double LNT = wifi.get("LNT").getAsDouble();
					String WORK_DTTM = wifi.get("WORK_DTTM").getAsString();
					
					TbPublicWifiInfoVO vo = new TbPublicWifiInfoVO();
					vo.setX_SWIFI_MGR_NO(X_SWIFI_MGR_NO);
					vo.setX_SWIFI_WRDOFC(X_SWIFI_WRDOFC);
					vo.setX_SWIFI_MAIN_NM(X_SWIFI_MAIN_NM);
					vo.setX_SWIFI_ADRES1(X_SWIFI_ADRES1);
					vo.setX_SWIFI_ADRES2(X_SWIFI_ADRES2);
					vo.setX_SWIFI_INSTL_FLOOR(X_SWIFI_INSTL_FLOOR);
					vo.setX_SWIFI_INSTL_TY(X_SWIFI_INSTL_TY);
					vo.setX_SWIFI_INSTL_MBY(X_SWIFI_INSTL_MBY);
					vo.setX_SWIFI_SVC_SE(X_SWIFI_SVC_SE);
					vo.setX_SWIFI_CMCWR(X_SWIFI_CMCWR);
					vo.setX_SWIFI_CNSTC_YEAR(X_SWIFI_CNSTC_YEAR);
					vo.setX_SWIFI_INOUT_DOOR(X_SWIFI_INOUT_DOOR);
					vo.setX_SWIFI_REMARS3(X_SWIFI_REMARS3);
					vo.setLAT(LAT);
					vo.setLNT(LNT);
					vo.setWORK_DTTM(WORK_DTTM);
					
					result = wifiInsert(vo);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while(start < count);
		
		return result;
	}
	
	// wifi 정보 저장
	public boolean wifiInsert(TbPublicWifiInfoVO vo) {
		boolean result = false;
		TbPublicWifiInfoDAO dao = new TbPublicWifiInfoDAO();
		result = dao.wifiInsert(vo);
		
		return result;
	}
	
	// wifi 총 개수
	public int wifiTotal() {
		int result = 0;
		TbPublicWifiInfoDAO dao = new TbPublicWifiInfoDAO();
		result = dao.total();
    	return result;
	}
	
	// wifi 조회 list
	public List<TbPublicWifiInfoVO> wifiList(double lat1, double lnt1){
		List<TbPublicWifiInfoVO> list = new ArrayList<TbPublicWifiInfoVO>();
		TbPublicWifiInfoDAO dao = new TbPublicWifiInfoDAO();
		list = dao.wifiList(lat1, lnt1);
		
		return list;
	}
	
	// wifi 정보 삭제
	public void wifiDelete(){
		TbPublicWifiInfoDAO dao = new TbPublicWifiInfoDAO();
		dao.wifiDelete();
	}
	
	// Log 저장
	public void wifiLogInsert(double lat1, double lnt1) {
		TbPublicWifiInfoDAO dao = new TbPublicWifiInfoDAO();
		dao.wifiLogInsert(lat1, lnt1);
		
	}
	
	
	// Log 조회 list
	public List<WifiLogVO> wifiLogList(){
		List<WifiLogVO> list = new ArrayList<WifiLogVO>();
		TbPublicWifiInfoDAO dao = new TbPublicWifiInfoDAO();
		list = dao.wifiLogList();
		
		return list;
	}
	
	// Log 삭제
	public boolean wifiLogDelete(int id) {
		boolean result = false;
		TbPublicWifiInfoDAO dao = new TbPublicWifiInfoDAO();
		result = dao.wifiLogDelete(id);
		
		return result;
	}
}
	

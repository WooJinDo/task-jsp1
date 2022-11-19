package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.vo.TbPublicWifiInfoVO;
import com.vo.WifiLogVO;

public class TbPublicWifiInfoDAO {
	
	// wifi 조회 리스트
	public List<TbPublicWifiInfoVO> wifiList(double lat1, double lnt1){
		List<TbPublicWifiInfoVO> list = new ArrayList<TbPublicWifiInfoVO>();
		
	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	
	    try {
	    	String dbFileUrl = "C:\\study\\dev\\jsp\\task-jsp1\\task1.db";
//		    	String dbFileUrl = "jdbc:sqlite:task1.db";
	    	connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileUrl);
	    	
	    	String sql = "select * from( "
		    					+ "	select t1.* "
		    					+ " , row_number() over (order by t1.distance asc) as rownum "
		    					+ "   from( "
				    			+ "		select t.* "
				    			+ "			, round(( 6371 * acos( cos( radians("+ lat1 +") ) * cos( radians( t.LAT ) ) "
				    			+ "	           * cos( radians( t.LNT ) - radians("+ lnt1 +") ) "
				    			+ "				+ sin( radians("+ lat1 +") ) * sin( radians( t.LAT ) ) ) ), 4) AS distance "
				    			+ "		from TbPublicWifiInfo t) t1 "
				    			+ "	order by t1.distance asc) t2 "
			    			+ "where rownum <= 20 ";
	
	        preparedStatement = connection.prepareStatement(sql);
	        
	        rs = preparedStatement.executeQuery();
	        
	        while(rs.next()) {
	        	TbPublicWifiInfoVO vo = new TbPublicWifiInfoVO();
	        	vo.setX_SWIFI_MGR_NO(rs.getString(1));
	        	vo.setX_SWIFI_WRDOFC(rs.getString(2));
	        	vo.setX_SWIFI_MAIN_NM(rs.getString(3));
	        	vo.setX_SWIFI_ADRES1(rs.getString(4));
	        	vo.setX_SWIFI_ADRES2(rs.getString(5));
	        	vo.setX_SWIFI_INSTL_FLOOR(rs.getString(6));
	        	vo.setX_SWIFI_INSTL_TY(rs.getString(7));
	        	vo.setX_SWIFI_INSTL_MBY(rs.getString(8));
	        	vo.setX_SWIFI_SVC_SE(rs.getString(9));
	        	vo.setX_SWIFI_CMCWR(rs.getString(10));
	        	vo.setX_SWIFI_CNSTC_YEAR(rs.getString(11));
	        	vo.setX_SWIFI_INOUT_DOOR(rs.getString(12));
	        	vo.setX_SWIFI_REMARS3(rs.getString(13));
	        	vo.setLAT(rs.getDouble(14));
	        	vo.setLNT(rs.getDouble(15));
	        	vo.setWORK_DTTM(rs.getString(16));
	        	vo.setDistance(rs.getDouble(17));
	        	vo.setRowNum(rs.getInt(18));
	        	
	        	list.add(vo);
	        	
	        }
	        	
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
		
		return list;
	}
	
	
	// wifi 총 개수
	public int total() {
		int result = 0;
		
	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	
	
	    try {
	    	String dbFileUrl = "C:\\study\\dev\\jsp\\task-jsp1\\task1.db";
//	    	String dbFileUrl = "jdbc:sqlite:task1.db";
	    	connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileUrl);
	    	
	    	String sql = "select count(*) from TbPublicWifiInfo ";
	
	        preparedStatement = connection.prepareStatement(sql);
	        
	        rs = preparedStatement.executeQuery();
	        if(rs.next()) {
	        	result = rs.getInt(1);
	        }
	        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
		
		return result;
	}

	
	// wifi 정보 저장
	public boolean wifiInsert(TbPublicWifiInfoVO vo) { 
		boolean result = false;
		
	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	
	
	    try {
	    	String dbFileUrl = "C:\\study\\dev\\jsp\\task-jsp1\\task1.db";
	    	connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileUrl);
	    	
	    	String sql = " insert into `TbPublicWifiInfo` (X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1 "
	    			+ " , X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE "
	    			+ " , X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM) " +
	                " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, vo.getX_SWIFI_MGR_NO());
	        preparedStatement.setString(2, vo.getX_SWIFI_WRDOFC());
	        preparedStatement.setString(3, vo.getX_SWIFI_MAIN_NM());
	        preparedStatement.setString(4, vo.getX_SWIFI_ADRES1());
	        preparedStatement.setString(5, vo.getX_SWIFI_ADRES2());
	        preparedStatement.setString(6, vo.getX_SWIFI_INSTL_FLOOR());
	        preparedStatement.setString(7, vo.getX_SWIFI_INSTL_TY());
	        preparedStatement.setString(8, vo.getX_SWIFI_INSTL_MBY());
	        preparedStatement.setString(9, vo.getX_SWIFI_SVC_SE());
	        preparedStatement.setString(10, vo.getX_SWIFI_CMCWR());
	        preparedStatement.setString(11, vo.getX_SWIFI_CNSTC_YEAR());
	        preparedStatement.setString(12, vo.getX_SWIFI_INOUT_DOOR());
	        preparedStatement.setString(13, vo.getX_SWIFI_REMARS3());
	        preparedStatement.setDouble(14, vo.getLAT());
	        preparedStatement.setDouble(15, vo.getLNT());
	        preparedStatement.setString(16, vo.getWORK_DTTM());
	
	        int val = preparedStatement.executeUpdate();
	
	        if (val != 0) {
	        	result = true;
	        } 
	        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
		return result;
	}
	
	
	// wifi 정보 삭제
	public void wifiDelete() { 
		
	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	
	
	    try {
	    	String dbFileUrl = "C:\\study\\dev\\jsp\\task-jsp1\\task1.db";
	    	connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileUrl);
	    	
	    	String sql = "delete from TbPublicWifiInfo";
	
	        preparedStatement = connection.prepareStatement(sql);
	        
	        preparedStatement.executeUpdate();
	        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
	}
		
	// Log 저장
	public void wifiLogInsert(double lat1, double lnt1) { 
		
	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	
	
	    try {
	    	String dbFileUrl = "C:\\study\\dev\\jsp\\task-jsp1\\task1.db";
	    	connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileUrl);
	    	
	    	String sql = "insert into WifiLog (lat, lnt, reg_DATE, STATUS) "
	    			+ " values (?, ?, datetime ( 'now', 'localtime'), 'N') ";
	
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setDouble(1, lat1);
	        preparedStatement.setDouble(2, lnt1);
	
	        preparedStatement.executeUpdate();
	
	        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
	}
	
	// Log 조회 list
	public List<WifiLogVO> wifiLogList(){
		List<WifiLogVO> list = new ArrayList<WifiLogVO>();
		
	    try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	
	    try {
	    	String dbFileUrl = "C:\\study\\dev\\jsp\\task-jsp1\\task1.db";
//			    	String dbFileUrl = "jdbc:sqlite:task1.db";
	    	connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileUrl);
	    	
	    	String sql = "select ID, LAT, LNT, REG_DATE, STATUS "
	    			+ "	from WifiLog "
	    			+ "	order by ID desc ";
	
	        preparedStatement = connection.prepareStatement(sql);
	        
	        rs = preparedStatement.executeQuery();
	        
	        while(rs.next()) {
	        	WifiLogVO vo = new WifiLogVO();
	        	vo.setID(rs.getInt(1));
	        	vo.setLAT(rs.getDouble(2));
	        	vo.setLNT(rs.getDouble(3));
	        	vo.setREG_DATE(rs.getString(4));
	        	vo.setSTATUS(rs.getString(5));
	        	
	        	list.add(vo);
	        }
	        	
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
		
		return list;
	}
	
	// Log 삭제
	public boolean wifiLogDelete(int id) {
		boolean result = false;
		
		try {
	        Class.forName("org.sqlite.JDBC");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	
	
	    try {
	    	String dbFileUrl = "C:\\study\\dev\\jsp\\task-jsp1\\task1.db";
	    	connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileUrl);
	    	
	    	String sql = "update WifiLog "
	    				+ " set "
	    				+ "	STATUS = 'Y' "
	    				+ " where id = ? ";
	
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setInt(1, id);
	        
	        int val = preparedStatement.executeUpdate();
	        
	        if(val != 0) {
	        	result = true;
	        }
	        
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            if (rs != null && !rs.isClosed()) {
	                rs.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (preparedStatement != null && !preparedStatement.isClosed()) {
	                preparedStatement.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	
	        try {
	            if (connection != null && !connection.isClosed()) {
	                connection.close();
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
		return result;
	}
}

package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.douzone.mysite.vo.GuestbookVo;
import com.douzone.mysite.vo.UserVo;


public class UserRepository {

	public Boolean insert(UserVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();

			String sql = "insert into user values(null, ?, ?, ?, ?, now())"; 

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			int count = pstmt.executeUpdate();

			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();

				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;
	}

	public UserVo findByEmailAndPassword(UserVo vo) {
		UserVo userVo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			conn = getConnection();

			String sql = "select no, name from user where email = ?  and password = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			
			rs = pstmt.executeQuery();

			while(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);


				userVo = new UserVo();

				userVo.setNo(no);
				userVo.setName(name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			try {
				if(rs != null)
					rs.close();

				if(pstmt != null)
					pstmt.close();

				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return userVo;
	}
	
	public UserVo findByNo(Long no) {
		UserVo userVo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			conn = getConnection();

			String sql = "select name, email, password, gender from user where no = ?";

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();

			while(rs.next()) {

				String name = rs.getString(1);
				String email = rs.getString(2);
				String password = rs.getString(3);
				String gender = rs.getString(4);

				userVo = new UserVo();

				userVo.setNo(no);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setPassword(password);
				userVo.setGender(gender);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			try {
				if(rs != null)
					rs.close();

				if(pstmt != null)
					pstmt.close();

				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return userVo;
	}
	
	public UserVo update(UserVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

			String sql = "update user set name = ?, password = ? where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setLong(3, vo.getNo());

			
			int count = pstmt.executeUpdate();

			result = count == 1;
			
			

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();

				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return vo;
	}
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			// 1. jdbc driver(myDriver) 로딩
			Class.forName("org.mariadb.jdbc.Driver");			
			// 2. 연결하기
			String url = "jdbc:mysql://192.168.1.113:3307/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		}  catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패" + e);
		} catch (SQLException e) {
			System.out.println("error : " + e);
		}
		
		return conn;
	}
	
}

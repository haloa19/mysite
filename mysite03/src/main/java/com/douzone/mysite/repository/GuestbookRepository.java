package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public List<GuestbookVo> findAll() {
		List<GuestbookVo> result = sqlSession.selectList("guestbook.findAll");
//		List<GuestbookVo> result = new ArrayList<>();
//
//		Connection conn = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//
//			conn = getConnection();
//
//			String sql = "select no, name, date_format(ng_date,'%Y-%m-%d %h:%i:%s'), contents, password from guestbook order by no desc";
//
//			pstmt = conn.prepareStatement(sql);
//
//			rs = pstmt.executeQuery(sql);
//
//			while(rs.next()) {
//				Long no = rs.getLong(1);
//				String name = rs.getString(2);
//				Timestamp regDate = rs.getTimestamp(3);
//				String contents = rs.getString(4);
//				String password = rs.getString(5);
//
//				GuestbookVo vo = new GuestbookVo();
//
//				vo.setNo(no);
//				vo.setName(name);
//				vo.setRegDate(regDate);
//				vo.setContents(contents);
//				vo.setPassword(password);
//
//				result.add(vo);
//			}
//
//		} catch (SQLException e) {
//			throw new GuestbookRepositoryException("error : " + e);
//		} finally {
//
//			try {
//				if(rs != null)
//					rs.close();
//
//				if(pstmt != null)
//					pstmt.close();
//
//				if(conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
		return result;
	}

	public int insert(GuestbookVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {
			conn = getConnection();

			String sql = 
					"insert" + 
					" into guestbook" + 
					" values(null, ?, ?, ?, now())";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,vo.getName());
			pstmt.setString(2, vo.getContents());
			pstmt.setString(3, vo.getPassword());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new GuestbookRepositoryException("error : " + e);
		} finally {	
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}				
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			
		}
		
		return count;
		
	}

	public int delete(Long no, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int count = 0;
		
		try {

			String correctPassword = findPassword(no);
			if(password.equals(correctPassword)) {
				conn = getConnection();

				String sql = "delete from guestbook where no=?"; 

				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, no);

				pstmt.execute();

				count = pstmt.executeUpdate();

			}
			else {
				System.out.println("비밀번호가 틀렸습니다");
				return -1;
			}

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new GuestbookRepositoryException("error : " + e);
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
		return count;
	}

	public String findPassword(Long no) {
		String password = null;
		List<GuestbookVo> list = new GuestbookRepository().findAll();

		for(GuestbookVo vo : list) {
			if(vo.getNo() == no) {
				password = vo.getPassword();
				break;
			}
		}
		return password;

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

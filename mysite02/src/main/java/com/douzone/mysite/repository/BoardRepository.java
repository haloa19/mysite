package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.GuestbookVo;

public class BoardRepository {
	
	public List<BoardVo> findAll(int page, int maxGroup) {
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			conn = getConnection();
			
			String sql = "select a.no, a.title, b.name, a.hit, a.reg_date, b.password, a.user_no, a.g_no, a.o_no, a.depth\r\n" + 
					"from board a\r\n" + 
					"	join user b on a.user_no = b.no\r\n" + 
					"where a.g_no <= ? - 5 * (? - 1) and a.g_no >= (? - 5 * (? - 1)) - 5 + 1\r\n" + 
					"order by a.g_no desc, a.o_no";

			pstmt = conn.prepareStatement(sql);

			pstmt.setDouble(1, maxGroup);
			pstmt.setInt(2, page);
			pstmt.setDouble(3, maxGroup);
			pstmt.setInt(4, page);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				Timestamp regDate = rs.getTimestamp(5);
				String password = rs.getString(6);
				Long userNo = rs.getLong(7);
				int gNo = rs.getInt(8);
				int oNo = rs.getInt(9);
				int depth = rs.getInt(10);

				BoardVo vo = new BoardVo();

				vo.setNo(no);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setPassword(password);
				vo.setUserNo(userNo);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);

				result.add(vo);
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
		return result;
	}
	
	public Boolean insert(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		int maxGroup = maxGroupNum() + 1;	
		
		try {
			conn = getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), ?, 1, 0, ?)"; 

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, maxGroup);
			pstmt.setLong(4, vo.getUserNo());		
			System.out.println();

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
	
	public boolean insertAddWrite(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?)"; 

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getgNo());
			pstmt.setInt(4,  vo.getoNo());
			pstmt.setInt(5,  vo.getDepth());
			pstmt.setLong(6, vo.getUserNo());			
			System.out.println();

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
	
	public BoardVo findByNo(Long num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo vo = null;
		
		try {

			conn = getConnection();

			String sql = "select no, title, contents, user_no from board where no = ? order by g_no, o_no";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long userNo = rs.getLong(4);

				vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserNo(userNo);
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
		return vo;
	}
	
	public List<BoardVo> findByKeyWord(int page, int maxGroup, String keyword) {
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			String sql = "select a.no, a.title, b.name, a.hit, a.reg_date, b.password, a.user_no, a.g_no, a.o_no, a.depth\r\n" + 
					"from(\r\n" + 
					"select *, @curRank := @curRank + 1 AS rank\r\n" + 
					"from board, (SELECT @curRank := 0) r\r\n" + 
					"where title like concat('%', ?, '%') and hit != -1\r\n" + 
					"order by reg_date) a join user b on a.user_no = b.no\r\n" + 
					"where a.rank <= ? - 5 * (? - 1) and a.rank >= (? - 5 * (? -1)) -5 + 1\r\n" + 
					"order by a.rank desc";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, keyword);
			pstmt.setDouble(2, maxGroup);
			pstmt.setInt(3, page);
			pstmt.setDouble(4, maxGroup);
			pstmt.setInt(5, page);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				int hit = rs.getInt(4);
				Timestamp regDate = rs.getTimestamp(5);
				String password = rs.getString(6);
				Long userNo = rs.getLong(7);
				int gNo = rs.getInt(8);
				int oNo = rs.getInt(9);
				int depth = rs.getInt(10);

				BoardVo vo = new BoardVo();

				vo.setNo(no);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setPassword(password);
				vo.setUserNo(userNo);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);

				result.add(vo);
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
		return result;
	}
	
	public Boolean update(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

			String sql = "update board set title = ?, contents = ? where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
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
		return result;
		
	}
	
	public Boolean delete(Long no, String password) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String correctPassword = findPassword(no);
			if(password.equals(correctPassword)) {
				conn = getConnection();

				String sql = "update board set hit = -1 where no = ?"; 

				pstmt = conn.prepareStatement(sql);

				pstmt.setLong(1, no);

				pstmt.execute();

				int count = pstmt.executeUpdate();

				result = count == 1;

			}

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
	
	/*
	 * DB에서 완전히 지울때만 필요
	 * */
	private boolean updateGno(Long no) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

			String sql = "update board \r\n" + 
					"set g_no = g_no - 1 \r\n" + 
					"where g_no > (select g_no \r\n" + 
					"				from(select g_no \r\n" + 
					"					 from board \r\n" + 
					"                     where no = ?) tmp)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

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

	public String findPassword(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String password = null;
		
		try {
			conn = getConnection();

			String sql = "select b.password\r\n" + 
					"from board a join user b on a.user_no = b.no\r\n" + 
					"where a.no = ?"; 

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				password = rs.getString(1);
			}
			

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

		return password;
	}
	
	public int maxGroupNum() {	
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxGroupNum = 0;
		
		try {
			conn = getConnection();

			String sql = "select max(g_no) from board"; 

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery(sql);
			
			while(rs.next()) {
				maxGroupNum = rs.getInt(1);
			}
			

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
		return maxGroupNum;
		
	}
	
	public void updateONo(int oNo, int gNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

			String sql = "update board set o_no =  o_no + 1 where g_no = ? and o_no > ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, gNo);
			pstmt.setLong(2, oNo);
			
			pstmt.executeUpdate();

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
		
	}
	
	public void updateHitNum(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

			String sql = "update board set hit = hit + 1 where no = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);	
			
			pstmt.executeUpdate();

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
		
	}
	
	public int findByKeyWordMaxGroup(String kwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int maxGroupNum = 0;

		try {
			conn = getConnection();

			String sql = "select max(b.rank)\r\n" + 
					"from(\r\n" + 
					"select *, @curRank := @curRank + 1 AS rank\r\n" + 
					"from board, (SELECT @curRank := 0) r\r\n" + 
					"where title like concat('%', ?, '%') and hit != -1\r\n" + 
					"order by reg_date) b\r\n" + 
					"order by b.rank desc"; 

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, kwd);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				maxGroupNum = rs.getInt(1);
			}			

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
		return maxGroupNum;
	}
	
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://127.0.0.1:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");		

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 

		return conn;	
	}

}

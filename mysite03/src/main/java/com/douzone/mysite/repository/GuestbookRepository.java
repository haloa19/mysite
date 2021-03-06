package com.douzone.mysite.repository;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestbookVo;


@Repository
public class GuestbookRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public List<GuestbookVo> findAll() {			
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public int insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}
	
	public void delete(long no) {
		sqlSession.insert("guestbook.delete", no);
	}

	public List<GuestbookVo> findAll(Long startNo) {
		return sqlSession.selectList("guestbook.findAllByNo", startNo);	
	}

	public int deleteSpa(GuestbookVo guestbookVo) {		
		return sqlSession.delete( "guestbook.deleteSpa", guestbookVo);
	}
	

}

package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository { 
	
	@Autowired
	private SqlSession sqlSession;	
	
	public int maxGroupNum(String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		return sqlSession.selectOne("board.maxGroupNum", map);		
	}

	public List<BoardVo> findAll(int nowPage, int maxGroup, String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("nowPage", nowPage);
		map.put("maxGroup", maxGroup);
		map.put("keyword", keyword);
		return sqlSession.selectList("board.findAll", map);
	}
	
	public int updateHitNum(Long no) {
		return sqlSession.update("board.updateHitNum", no);		
	}
	
	public BoardVo findByNo(Long no) {
		return sqlSession.selectOne("board.findByNo", no);		
	}

	public BoardVo findParentNoInfo(Long parentNo) {
		return sqlSession.selectOne("board.findParentNoInfo", parentNo);		
	}
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("board.write", vo);
	}
	
	public int delete(Long no) {
		return sqlSession.delete("board.delete", no);
	}
	
	public int update(BoardVo boardVo) {
		return sqlSession.update("board.update", boardVo);
	}

}

package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public SiteVo findInfo() {
		return sqlSession.selectOne("site.findInfo");
	}

	public void updateInfo(SiteVo siteVo) {
		sqlSession.update("site.updateInfo", siteVo);
	}

}

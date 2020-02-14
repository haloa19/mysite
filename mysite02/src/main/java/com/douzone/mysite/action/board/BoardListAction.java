package com.douzone.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 게시판 처음 접속 시 무조건 1페이지 요청
		// case1. 검색어가 있는 경우
		// case2. 검색어가 없는 경우
		int maxGroup = 0;
		String keyword = request.getParameter("kwd");
		System.out.println("처음 검색시  " + keyword);
		
		String keyword2 = request.getParameter("keyword");
//		System.out.println("다음 검색시 : " + keyword2);
	
		if(keyword != null) { // 처음 검색해서 keyword가 존재하는 경우
			maxGroup = new BoardRepository().findByKeyWordMaxGroup(keyword);
		} else if(keyword2 != null) { // 검색 후 페이지 전환 했을 경우
			keyword = keyword2;
			maxGroup = new BoardRepository().findByKeyWordMaxGroup(keyword);
		} else { // 검색 없이 전체 리스트
			maxGroup = new BoardRepository().maxGroupNum();
		}
		
		// 페이지 정보 초기값
		int nowPage = 1;
		int beginPage = 1;
		double totalPage = Math.ceil((double)maxGroup/5);
		int endPage = (int)totalPage; // < 1 > or <1, 2>
		
		
		if(request.getParameter("page") != null) { // 페이지 이동이 이었을 경우
			nowPage = Integer.parseInt(request.getParameter("page"));
			if(nowPage != 1 && (nowPage - 1) % 3 == 0) { // 시작페이지 변경이 필요한 경우
				beginPage = Integer.parseInt(request.getParameter("bpage")) + 1;
			}
		}
		
		if(totalPage > 2) { // 보여지는 마지막 숫자 (토탈 페이지 아님)
			endPage = beginPage + 2;
		}		

		List<BoardVo> list = null;
		
		if(request.getParameter("page") == null) { // 페이지 이동이 없는 경우
			if(keyword == null) { // 검색어가 없는 경우
				list = new BoardRepository().findAll(1, maxGroup);
			} else { // 검색어가 있는 경우
				list = new BoardRepository().findByKeyWord(1, maxGroup, keyword);
			}			
		} else {
			if(keyword == null) {
				maxGroup = new BoardRepository().maxGroupNum();
				list = new BoardRepository().findAll(nowPage, maxGroup);

			} else {
				maxGroup = new BoardRepository().findByKeyWordMaxGroup(keyword);
				list = new BoardRepository().findByKeyWord(Integer.parseInt(request.getParameter("page")), maxGroup, keyword);
			}
			if(keyword2 != null) {
				maxGroup = new BoardRepository().findByKeyWordMaxGroup(keyword);
				list = new BoardRepository().findByKeyWord(Integer.parseInt(request.getParameter("page")), maxGroup, keyword);
			}

		}

		request.setAttribute("list", list);
		request.setAttribute("groupNum", maxGroup);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("keyword", keyword);
//		request.setAttribute("keyword", keyword);
		
		if(keyword != null) {
			WebUtil.forward("/WEB-INF/views/board/searchlist.jsp", request, response);
		} else {
			WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
		}
		

	}

}

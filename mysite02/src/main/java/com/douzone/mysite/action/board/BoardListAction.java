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
		// 0. 게시판 처음 접속 시 무조건 1페이지 요청
		int maxGroup = 0;
		String keyword = request.getParameter("kwd"); // 검색어가 있는 경우 검색어를 처음 가져올때
		String keyword2 = request.getParameter("keyword");  // 검색어가 있고 페이지 전환이 있는 경우 (kwd로 가져온 값은 null이 되기 때문에)
	
		// 1. 검색어 유무에 따른 group개수
		if(keyword != null) { // 처음 검색해서 keyword가 존재하는 경우
			maxGroup = new BoardRepository().findByKeyWordMaxGroup(keyword);
		} else if(keyword2 != null) { // 검색 후 페이지 전환 했을 경우
			keyword = keyword2;
			maxGroup = new BoardRepository().findByKeyWordMaxGroup(keyword);
		} else { // 검색 없이 전체 리스트
			maxGroup = new BoardRepository().maxGroupNum();
		}
		
		// 2. 페이지 정보 초기값 설정
		int nowPage = 1;
		int beginPage = 1;
		double totalPage = Math.ceil((double)maxGroup/5);
		int endPage = (int)totalPage; // < 1 > or <1, 2>		
		List<BoardVo> list = null;	
		
		// 3. 페이지 정보에 따른 리스트 가져오기
		// 3-1. 페이지 이동이 있는 경우
		if(request.getParameter("page") != null) {
			nowPage = Integer.parseInt(request.getParameter("page"));
			if(nowPage != 1 && (nowPage - 1) % 3 == 0) { // 시작페이지 변경이 필요한 경우
				beginPage = Integer.parseInt(request.getParameter("bpage")) + 1;
			}
			
			// 3-1-1. 검색어가 있는 경우
			if(keyword != null || keyword2 != null) {
				list = new BoardRepository().findByKeyWord(Integer.parseInt(request.getParameter("page")), maxGroup, keyword);
			} else { // 3-1-2. 검색어가 없는 경우
				list = new BoardRepository().findAll(nowPage, maxGroup);
			}			
			
		} else { // 3-2. 페이지 이동이 없는 경우
			if(keyword == null) { // 3-2-1. 검색어가 없는 경우
				list = new BoardRepository().findAll(1, maxGroup);
			} else { // 3-2-2. 검색어가 있는 경우
				list = new BoardRepository().findByKeyWord(1, maxGroup, keyword);
			}	

		}
		
		if(totalPage > 2) { // 보여지는 마지막 숫자 (토탈 페이지 아님), totlapage가 1 or 2일 경우 totalpage == endpage
			endPage = beginPage + 2;
		}		

		request.setAttribute("list", list);
		request.setAttribute("groupNum", maxGroup);
		request.setAttribute("nowPage", nowPage);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("keyword", keyword);
		
		if(keyword != null) {
			WebUtil.forward("/WEB-INF/views/board/searchlist.jsp", request, response);
		} else {
			WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
		}
	}

}

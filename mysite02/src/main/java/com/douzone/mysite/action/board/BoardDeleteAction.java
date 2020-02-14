package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class BoardDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long no = Long.parseLong(request.getParameter("no")); // 게시판 번호
		int gNo = Integer.parseInt(request.getParameter("gNno"));
		String password = request.getParameter("password");

		// no에 해당하는 dedpth 구하는 쿼리
		// 현재 no의 oNo에 +1한 depth 구하는 쿼리 
		
		new BoardRepository().findDepth(no);
		new BoardRepository().delete(no, password);
		
		WebUtil.redirect(request.getContextPath() + "/board", request, response);

	}

}

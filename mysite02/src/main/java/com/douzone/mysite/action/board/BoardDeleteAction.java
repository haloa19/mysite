package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class BoardDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long no = Long.parseLong(request.getParameter("no"));
		String password = request.getParameter("password");
		Boolean result = new BoardRepository().delete(no, password);
		
		if(result) {
			WebUtil.redirect(request.getContextPath() + "/board", request, response);
		} else {
			request.setAttribute("result", "fail");
			WebUtil.forward("/WEB-INF/views/board/deleteform.jsp", request, response);
		}

	}

}

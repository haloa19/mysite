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

public class BoardViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long no = Long.parseLong(request.getParameter("no"));
		
		new BoardRepository().updateHitNum(no);
		BoardVo boardVo = new BoardRepository().findByNo(no);
	
		System.out.println(no);
		System.out.println(boardVo);

		request.setAttribute("listNo", boardVo);
		
		WebUtil.forward("/WEB-INF/views/board/view.jsp", request, response);
	}

}

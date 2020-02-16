package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class BoardModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long no = Long.parseLong(request.getParameter("no"));
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		
		BoardVo boardVo = new BoardVo();		
		boardVo.setNo(no);
		boardVo.setTitle(title);
		boardVo.setContents(contents);
		
		new BoardRepository().update(boardVo);
		
		WebUtil.redirect(request.getContextPath() + "/board", request, response);

	}

}

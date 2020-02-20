package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class BoardInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		Long userNo = Long.parseLong(request.getParameter("userNo"));	
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(userNo);
		
		int gNo = 0;
		int oNo = 0;
		int depth = 0;
		Long no = 0L;
		
		if(request.getParameter("kind").equals("addwrite")) {
			gNo = Integer.parseInt(request.getParameter("gNo"));
			oNo = Integer.parseInt(request.getParameter("oNo"));
			depth = Integer.parseInt(request.getParameter("depth"));
			no = Long.parseLong((request.getParameter("no")));
			
			vo.setgNo(gNo);
			vo.setoNo(oNo);
			vo.setDepth(depth);
			vo.setNo(no);
			
			new BoardRepository().insertAddWrite(vo);
			
		} else {
			new BoardRepository().insert(vo);
		}

		WebUtil.redirect(request.getContextPath() + "/board", request, response);

	}

}

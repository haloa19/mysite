package com.douzone.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class BoardInsertFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {	
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String kind = request.getParameter("kind");
		
		if(authUser == null) {
			request.setAttribute("kind", kind);
			WebUtil.forward("/WEB-INF/views/user/loginform.jsp", request, response);
			return;
		}
		
		if (kind.equals("newwrite")) {
			request.setAttribute("kind", kind);
			WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);
		} else {
			// 부모 정보
			int gNo = Integer.parseInt(request.getParameter("gNo"));
			int oNo = Integer.parseInt(request.getParameter("oNo"));
			int depth = Integer.parseInt(request.getParameter("depth"));
			Long no = Long.parseLong(((request.getParameter("no"))));

			if(depth > -1) {
				new BoardRepository().updateONo(oNo, gNo);
			}
			
			BoardVo boardVo = new BoardVo();
			boardVo.setgNo(gNo);
			boardVo.setDepth(depth+1);
			boardVo.setoNo(oNo+1);
			boardVo.setNo(no);		
			
			request.setAttribute("boardVo", boardVo);
			request.setAttribute("kind", kind);
			
			WebUtil.forward("/WEB-INF/views/board/write.jsp", request, response);
		}
			
		
	}

}

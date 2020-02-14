package com.douzone.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.action.board.BoardActionFactory;
import com.douzone.mysite.action.guestbook.GuestbookActionFactory;
import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;
import com.douzone.web.action.Action;
import com.douzone.web.action.ActionFactory;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String actionName = request.getParameter("a");
		
		if(actionName == null) {
			actionName = "b";
		}
		ActionFactory af = new BoardActionFactory();
		Action action = af.getAction(actionName);
		action.execute(request, response);
		
		
//
//			// default 요청 처리(list)
//			List<GuestbookVo> list = new GuestbookRepository().findAll();	
//
//			request.setAttribute("list", list);
//			request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp").forward(request, response);

	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

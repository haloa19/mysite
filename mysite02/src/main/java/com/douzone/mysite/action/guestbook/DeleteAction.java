package com.douzone.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Long no = Long.parseLong(request.getParameter("no"));
		String password = request.getParameter("password");

		new GuestbookRepository().delete(no, password);

//		response.sendRedirect(
//		request.getContextPath() + "/guestbook");
		
		WebUtil.redirect(request.getContextPath() + "/guestbook", request, response);
	}

}

package com.douzone.mysite.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class InsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name = request.getParameter("name");
		String contents = request.getParameter("content");
		String password = request.getParameter("password");

		GuestbookVo vo = new GuestbookVo();

		vo.setName(name);
		vo.setContents(contents);
		vo.setPassword(password);

		new GuestbookRepository().insert(vo);

		WebUtil.redirect(request.getContextPath() + "/guestbook", request, response);

	}

}

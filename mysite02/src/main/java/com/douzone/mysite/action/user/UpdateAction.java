package com.douzone.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.action.Action;
import com.douzone.web.util.WebUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Long no = Long.parseLong(request.getParameter("no"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		UserVo userVo = new UserVo();

		userVo.setNo(no);
		userVo.setName(name);
		userVo.setEmail(email);
		userVo.setPassword(password);
		userVo.setGender(gender);
		
		new UserRepository().update(userVo);
		
		WebUtil.redirect(request.getContextPath(), request, response);
	}

}

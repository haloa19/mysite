package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value= {"", "/list"}, method=RequestMethod.GET)
	public String list(HttpSession session,
						Model model, 
						@RequestParam(value="npage", required=true, defaultValue="1") int nowPage,
						@RequestParam(value="bpage", required=true, defaultValue="1") int beginPage,
						@RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		
		Map<String, Object> map = boardService.list(model, nowPage, beginPage, keyword);
		model.addAllAttributes(map);
		UserVo authUser = (UserVo)session.getAttribute("authUser");		
		session.setAttribute("authUser", authUser);	
		return "board/list";
	}
	
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(Model model, 
					   @PathVariable("no") Long no) {
		boardService.view(model, no);
		return "board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session) {
		//////////////////////////접근제어/////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
        /////////////////////////////////////////////////////////////////
		return "board/write";
	}
	
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String reply(HttpSession session, 
						Model model, 
						@PathVariable("no") Long no) {
		//////////////////////////접근제어/////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
        /////////////////////////////////////////////////////////////////
		model.addAttribute("pNo", no);
		return "board/write";
	}

	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session,
						@ModelAttribute BoardVo vo,
						@RequestParam(value="pNo", required=false, defaultValue="0") Long parentNo) {
		//////////////////////////접근제어/////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
        /////////////////////////////////////////////////////////////////
		Long userNo = authUser.getNo();
		boardService.write(vo, parentNo, userNo);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(HttpSession session, 
						Model model, 
						@PathVariable("no") Long no) {
		//////////////////////////접근제어/////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
        /////////////////////////////////////////////////////////////////
		boardService.delete(no);
		return "redirect:/board";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(HttpSession session, 
						Model model, 
						@PathVariable("no") Long no) {
		//////////////////////////접근제어/////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
        /////////////////////////////////////////////////////////////////
		BoardVo vo = boardService.view(model, no);
		model.addAttribute("listNo", vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(HttpSession session, 
						@PathVariable("no") Long no,
						BoardVo boardVo) {
		//////////////////////////접근제어/////////////////////////////////
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
        /////////////////////////////////////////////////////////////////
		boardVo.setUserNo(no);
		boardService.update(boardVo);
		return "redirect:/";
	}	

}

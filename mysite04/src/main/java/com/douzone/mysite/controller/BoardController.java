package com.douzone.mysite.controller;

import java.util.Map;

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
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value= {"", "/list"}, method=RequestMethod.GET)
	public String list(@AuthUser UserVo authUser,
						Model model, 
						@RequestParam(value="npage", required=true, defaultValue="1") int nowPage,
						@RequestParam(value="bpage", required=true, defaultValue="1") int beginPage,
						@RequestParam(value="keyword", required=false, defaultValue="") String keyword) {
		
		Map<String, Object> map = boardService.list(model, nowPage, beginPage, keyword);
		model.addAllAttributes(map);
		
		return "board/list";
	}
	
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(Model model, 
					   @PathVariable("no") Long no) {
		boardService.view(model, no);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(@AuthUser UserVo authUser) {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/reply/{no}", method=RequestMethod.GET)
	public String reply(@AuthUser UserVo authUser, 
						Model model, 
						@PathVariable("no") Long no) {
		model.addAttribute("pNo", no);
		return "board/write";
	}

	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser,
						@ModelAttribute BoardVo vo,
						@RequestParam(value="pNo", required=false, defaultValue="0") Long parentNo) {
		Long userNo = authUser.getNo();
		boardService.write(vo, parentNo, userNo);
		
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@AuthUser UserVo authUser, 
						Model model, 
						@PathVariable("no") Long no) {
		boardService.delete(no);
		
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/modify/{no}", method=RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser, 
						Model model, 
						@PathVariable("no") Long no) {
		boardService.view(model, no);
		
		return "board/modify";
	}
	
	@Auth
	@RequestMapping(value="/modify/{no}", method=RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, 
						@PathVariable("no") Long no,
						BoardVo boardVo) {
		//boardVo.setUserNo(no);
		//boardVo.setNo(no);
		boardService.update(boardVo);
		
		return "redirect:/board";
	}	

}

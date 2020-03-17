package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.security.Auth;
import com.douzone.security.AuthUser;

@Auth("ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {	
	
	@Autowired
	private SiteService siteService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = siteService.getSiteInfo();
		model.addAttribute("siteVo", vo);
		return "admin/main";
	}
	
	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String update(
			SiteVo siteVo,
			@RequestParam(value="file1") MultipartFile multipartFile) {
		if(multipartFile.isEmpty()) {
	 		SiteVo vo = siteService.getSiteInfo();
	 		siteVo.setProfile(vo.getProfile());
		} else {
			String url = siteService.restore(multipartFile);
			siteVo.setProfile(url);
		}		
		
		siteService.updateSiteInfo(siteVo);

		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}

}

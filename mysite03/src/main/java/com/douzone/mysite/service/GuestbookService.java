package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	
	@Autowired
	private GuestbookRepository guestbookRepository;

	public void list(Model model) {
		List<GuestbookVo> list = guestbookRepository.findAll();
		model.addAttribute("list", list);	
	}

	public boolean add(GuestbookVo vo) {
		int count = guestbookRepository.insert(vo);
		return count == 1;
	}

	public boolean delete(Long no, String password) {
		int count = guestbookRepository.delete(no, password);
		return count == 1;		
	}

}

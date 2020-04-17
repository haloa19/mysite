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

	public void delete(Long no, String password) {
		String correctPassword = findPassword(no);
		
		if(password.equals(correctPassword)) {
			guestbookRepository.delete(no);			
		}
	}
	
	public String findPassword(Long no) {
		String password = null;
		List<GuestbookVo> list = guestbookRepository.findAll();

		for(GuestbookVo vo : list) {
			if(vo.getNo() == no) {
				password = vo.getPassword();
				break;
			}
		}
		return password;
	}

	public List<GuestbookVo> getMessageList(Long startNo) {
		return guestbookRepository.findAll(startNo);
		
	}

	public Boolean deleteSpa(Long no, String password) {	
		return 1 == guestbookRepository.deleteSpa( new GuestbookVo(no, password) );
	}

}

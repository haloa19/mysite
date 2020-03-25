package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;

	/* 1. 게시글 */
	public Map<String, Object> list(Model model, int nowPage, int beginPage, String keyword) {
		// 0. 게시판 처음 접속 시 무조건 1페이지 요청
		// 1. 검색어 유무에 따른 group개수
		int maxGroup = boardRepository.maxGroupNum(keyword);
		
		double totalPage = Math.ceil((double)maxGroup/5);
		int endPage = (int)totalPage; // < 1 > or <1, 2>		
		List<BoardVo> list = null;	
		
		if(nowPage != 1 && nowPage-1 == beginPage + 2) { // 시작페이지 변경이 필요한 경우
			beginPage = beginPage + 1;
		}
		if(nowPage+1 == beginPage) {
			beginPage = beginPage - 1;
		}
		if(totalPage > 2) { // 보여지는 마지막 숫자 (토탈 페이지 아님), totlapage가 1 or 2일 경우 totalpage == endpage
			endPage = beginPage + 2;
		}
		
		list = boardRepository.findAll(nowPage, maxGroup, keyword);	
		
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("maxGroup", maxGroup);
		map.put("nowPage", nowPage);
		map.put("beginPage", beginPage);
		map.put("endPage", endPage);
		map.put("totalPage", totalPage);
		map.put("keyword", keyword);
		
		model.addAllAttributes(map);
		
		return map;
		
	}

	/* 2. 글 내용 보기 */
	public BoardVo view(Model model, Long no) {
		boardRepository.updateHitNum(no);
		BoardVo boardVo = boardRepository.findByNo(no);
		model.addAttribute("listNo", boardVo);		
		return boardVo;
	}

	/* 3. 글 작성 */
	public boolean write(BoardVo vo, Long parentNo, Long userNo) {
		int groupNo = boardRepository.maxGroupNum("") + 1;
		int orderNo = 1;
		int depth = 0;
		
		if(parentNo > 0) {
			BoardVo boardVo = boardRepository.findParentNoInfo(parentNo);
			groupNo = boardVo.getgNo();
			orderNo = boardVo.getoNo() + 1;
			depth = boardVo.getDepth() + 1;
		}
		
		vo.setgNo(groupNo);
		vo.setoNo(orderNo);
		vo.setDepth(depth);
		vo.setUserNo(userNo);
		
		int count = boardRepository.insert(vo);		
		return count == 1;
	}

	/* 4. 글 삭제 */
	public void delete(Long no) {
		boardRepository.delete(no);		
	}
	
	/* 5. 글 수정 */
	public boolean update(BoardVo vo) {
		int count = boardRepository.update(vo);
		return count == 1;
	}

}

package com.sist.app;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.service.MemoService;
import mybatis.vo.MemoVO;

@Controller
public class MemoController {

	@Autowired
	private MemoService mapper2;
	
	@RequestMapping("memo")
	public ModelAndView memo() {
		ModelAndView mv = new ModelAndView();
		
		MemoVO[] m_ar = mapper2.getList();
		
		mv.addObject("m_ar", m_ar);
		
		mv.setViewName("memo_list");
		
		return mv;
	}
}

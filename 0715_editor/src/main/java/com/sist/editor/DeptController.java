package com.sist.editor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.DeptDAO;
import mybatis.vo.DeptVO;

@Controller
public class DeptController {
	
	@Autowired
	private DeptDAO d_dao;
	
	@RequestMapping("/dept_list")
	private ModelAndView dept_list() {
		ModelAndView mv = new ModelAndView();
		
		DeptVO[] d_ar = d_dao.getList();
		
		mv.addObject("d_ar", d_ar);
		
		mv.setViewName("dept_list");
		
		return mv;
	}
	
	@RequestMapping("/dept_search")
	private ModelAndView dept_search(String searchType, String searchValue) {
		ModelAndView mv = new ModelAndView();
		
		DeptVO[] d_ar = d_dao.search(searchType, searchValue);
		
		mv.addObject("d_ar", d_ar);
		
		mv.setViewName("dept_list");
		
		return mv;
	}
	
}

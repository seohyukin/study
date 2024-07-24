package com.sist.ex2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.EmpDAO;
import mybatis.vo.EmpVO;

@Controller
public class EmpController {

	@Autowired
	private EmpDAO e_dao;
	
	//request가 필요할 때 생성하는 법
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping("/emp_list")
	public ModelAndView emp_list() {
		ModelAndView mv = new ModelAndView();
		
		EmpVO[] ar = e_dao.getList();
		System.out.println("emp_list.length:" + ar.length);
		mv.addObject("ar", ar);
		
		mv.setViewName("emp_list");
		
		return mv;
	}
	
	//emp_list.jsp에 form의 action에 "emp_search"를 지정해줬으므로 인자를 쉽게 받을 수 있ㅇ,ㅁ
	@RequestMapping("/emp_search")
	public ModelAndView emp_search(String searchType, String searchValue) {
		ModelAndView mv = new ModelAndView();
		
		EmpVO[] ar = e_dao.search(searchType, searchValue);
		
		//같은 jsp를 쓰려면 mv에 저장되는 "ar"같은 이름이 동일해야 한다. (foreach로 빼와야 하기 때문)
		mv.addObject("ar", ar);
		
		mv.setViewName("emp_list");
		
		return mv;
	}
}

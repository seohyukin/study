package com.sist.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import mybatis.service.EmpService;
import mybatis.service.MyMapper;
import mybatis.vo.EmpVO;

@Controller
public class EmpController {

	@Autowired
	private EmpService mapper1; //자료형은 EmpService, MyMapper 둘 다 가능하다.
	
	@RequestMapping("emp_list")
	public ModelAndView empList() {
		ModelAndView mv = new ModelAndView();
		
		EmpVO[] e_ar = mapper1.getList();
		
		mv.addObject("e_ar", e_ar);
		
		//비동기식O(emp_list)
//		mv.setViewName("emp_list");
		
		//비동기식X(emp_list2)
		mv.setViewName("emp_list2");
		
		return mv;
	}
	
	//비동기식O(emp_list)
//	@RequestMapping("emp_search")
//	@ResponseBody
//	public Map<String, Object> search(String searchType, String searchValue) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		EmpVO[] e_ar = mapper1.search(searchType, searchValue);
//		
//		map.put("e_ar", e_ar);
//		
//		return map;
//	}
	
	//비동기식X(emp_list2)
	@RequestMapping("emp_search")
	@ResponseBody
	public ModelAndView search(String searchType, String searchValue) {
		ModelAndView mv = new ModelAndView();
		
		EmpVO[] e_ar = mapper1.search(searchType, searchValue);
		
		mv.addObject("e_ar", e_ar);
		
		mv.setViewName("emp_list2");
		
		return mv;
	}
}

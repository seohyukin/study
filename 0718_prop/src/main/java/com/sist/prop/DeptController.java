package com.sist.prop;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.DeptDAO;
import mybatis.vo.DeptVO;

@Controller
public class DeptController {

	@Autowired
	private DeptDAO d_dao;
	
	@RequestMapping("dept")
	public ModelAndView dept() {
		ModelAndView mv = new ModelAndView();
		
		//부서목록 가져오기
		DeptVO[] d_ar = d_dao.total();
		
		mv.addObject("d_ar", d_ar);
		
		mv.setViewName("dept/list");
		
		return mv;
	}
	
//	REST API(데이터만 보내는 것)가 보통 아래와 같은 방식을 활용
	@RequestMapping("dept/search")
	@ResponseBody
	public Map<String, Object> search(String searchType, String searchValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		System.out.println("st:" + searchType);
		System.out.println("sv:" + searchValue);
		
		DeptVO[] d_ar = d_dao.search(searchType, searchValue);
		
		map.put("d_ar", d_ar);
		map.put("len", d_ar.length);
		
		return map;
	}
}

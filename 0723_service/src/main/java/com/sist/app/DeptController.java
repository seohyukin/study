package com.sist.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import mybatis.service.DeptService;
import mybatis.vo.DeptVO;

@Controller
public class DeptController {

	@Autowired
	private DeptService mapper2;
	
	@RequestMapping("dept")
	public ModelAndView dept() {
		ModelAndView mv = new ModelAndView();
		
		DeptVO[] d_ar = mapper2.getList();
		
		mv.addObject("d_ar", d_ar);
		
		mv.setViewName("dept_list");
		
		return mv;
	}
	
	@RequestMapping("dept_search")
	@ResponseBody
	public Map<String, Object> search(String searchType, String searchValue) {
	Map<String, Object> map = new HashMap<String, Object>();
	
	DeptVO[] d_ar = mapper2.search(searchType, searchValue);
	
	map.put("d_ar", d_ar);
	map.put("len", d_ar.length);
	
	return map;
}

}

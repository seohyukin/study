package com.sist.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.service.BbsService;
import mybatis.vo.BbsVO;

@Controller
public class BbsController {

	@Autowired
	private BbsService mapper1;
	
	@RequestMapping("bbs")
	public ModelAndView bbs() {
		ModelAndView mv = new ModelAndView();
		
		BbsVO[] b_ar = mapper1.getList();
		
		mv.addObject("b_ar", b_ar);
		
		mv.setViewName("bbs_list");
		
		return mv;
	}
}

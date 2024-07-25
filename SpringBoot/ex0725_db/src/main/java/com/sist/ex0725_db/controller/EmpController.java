package com.sist.ex0725_db.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sist.ex0725_db.service.EmpService;
import com.sist.ex0725_db.vo.EmpVO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class EmpController {
    
    @Autowired
    private EmpService e_service;

    @RequestMapping(value="/emp", method=RequestMethod.GET)
    public ModelAndView all() {
        ModelAndView mv = new ModelAndView();

        EmpVO[] e_ar = e_service.getAll(); //(1,10)

        mv.addObject("e_ar", e_ar);
        mv.setViewName("all");

        return mv;
    }

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public ModelAndView list(String begin, String end) {
        ModelAndView mv = new ModelAndView();

        EmpVO[] e_ar = e_service.getList(begin, end);

        mv.addObject("e_ar", e_ar);
        mv.setViewName("list");

        return mv;
    }

    @RequestMapping(value="search", method=RequestMethod.POST)
	public ModelAndView search(String searchType, String searchValue) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("searchType", searchType);
        map.put("searchValue", searchValue);

        EmpVO[] e_ar = e_service.search(searchType, searchValue);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("e_ar", e_ar);
		mv.setViewName("all");
		
		return mv;
	}


    //비동기식 O
    @RequestMapping(value="emp_search", method=RequestMethod.POST)
    public Map<String, Object> search_ajax(String searchType, String searchValue) {
        Map<String, Object> map = new HashMap<String, Object>();

        EmpVO[] e_ar = e_service.search_ajax(searchType, searchValue);

        map.put("e_ar", e_ar);

        return map;
    }

    
}

package com.sist.ex0725_db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sist.ex0725_db.service.DeptService;
import com.sist.ex0725_db.vo.DeptVO;


@Controller
public class DeptController {
    
    @Autowired
    private DeptService d_service;

    @RequestMapping(value="dept", method=RequestMethod.GET)
    public ModelAndView all() {
        ModelAndView mv = new ModelAndView();

        DeptVO[] d_ar = d_service.getAll();

        mv.addObject("d_ar", d_ar);

        mv.setViewName("dept");

        return mv;
    }
    
}

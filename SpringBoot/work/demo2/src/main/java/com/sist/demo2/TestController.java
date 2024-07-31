package com.sist.demo2;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import data.vo.DataVO;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class TestController {
    
    @Value("${msg}")
    private String msg;

    @Value("${v1}")
    private int v1;

    @Value("${v2}")
    private int v2;

    @GetMapping("msg")
    public String getMsg() {
        DataVO dvo = new DataVO();
        dvo.setStr(msg);

        return dvo.getStr();
    }
    
    @GetMapping("test")
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView();

        mv.addObject("msg", msg);

        mv.setViewName("test");

        return mv;
    }

    @GetMapping("today")
    public ModelAndView date() {
        ModelAndView mv = new ModelAndView();

        Date date = new Date();
        SimpleDateFormat todayFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

        String today = todayFormat.format(date);

        mv.addObject("today", today);

        mv.setViewName("test");

        return mv;
    }

    @GetMapping("test1")
    public ModelAndView add1() {
        ModelAndView mv = new ModelAndView();

        DataVO dvo = new DataVO();
        dvo.setV1(v1);
        dvo.setV2(v2);

        int v = dvo.getV1() + dvo.getV2();

        mv.addObject("v", v);
        mv.setViewName("test");

        return mv;
    }

    @GetMapping("test2")
    public String add2(@RequestParam int v1, @RequestParam int v2) {
        int v3 = v1 + v2;
        return "합: " + v3;
    }
    
    
    
    
}

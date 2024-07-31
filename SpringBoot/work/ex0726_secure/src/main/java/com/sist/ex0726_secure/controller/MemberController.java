package com.sist.ex0726_secure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sist.ex0726_secure.service.MemberService;
import com.sist.ex0726_secure.vo.MemVO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
public class MemberController {
    
    //DB활용을 위한 service

    //로그인처리를 위한 세션 객체
    @Autowired
    private HttpSession session;

    @Autowired
    private MemberService service;

    @RequestMapping
    public String index() {
        return "index";
    }

    @RequestMapping(value="login", method=RequestMethod.GET) //@GetMapping("login")과 같은 의미
    public String login() {
        return "login";
    }

    @GetMapping("reg")
    public String reg() {
        return "reg";
    }

    @PostMapping("reg")
    public ModelAndView reg(MemVO mvo) {
        System.out.println(mvo.getM_id() + "/" + mvo.getM_pw() + "/" + mvo.getM_name());
        
        ModelAndView mv = new ModelAndView();
        //서비스를 이용하여 회원등록!
        int cnt = service.reg(mvo);

        mv.addObject("cnt", cnt);
        mv.setViewName("login");
        
        return mv;
    }
    
    @PostMapping("/reqLogin")
    public ModelAndView login(MemVO mvo) {
        System.out.println(mvo.getM_id()+
        "/"+mvo.getM_pw());
        ModelAndView mv = new ModelAndView();

        //서비스를 이용하여 로그인 인증확인!!
        MemVO vo = service.login(mvo);
        if(vo != null)
            session.setAttribute("mvo", vo);
        else
            mv.addObject("status", "1");

        mv.setViewName("redirect:/");
        return mv;
    }

    @RequestMapping("/mem_logout")
    public String logout() {
        System.out.println("LOGOUT");
        session.removeAttribute("mvo");

        return "redirect:/";
    }
    
}

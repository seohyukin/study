package com.sist.inter;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	
	@Autowired
	private HttpSession session;
	
	//contentType의 값이 null, application, multipart로 구분하는 방법말고 다른 방법
	
	//기본이 get방식이므로 method를 지정해주지 않으면 get방식으로 분류됨 (nav.jsp의 <a href="/login">)
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	//get방식이 아닌 post방식이므로 value값을 주어서 method값을 post로 준다. (login.jsp의 <form action="login" method="post">)
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView login(String m_id, String m_pw) { //login.jsp의 name이 m_id인 text를 인자로 받는다.
		//application, multipart는 함수 안에서 구분해야 함
		ModelAndView mv = new ModelAndView();
		
		//id와 pw를 가지고 DB 검증
		
		//세션 처리
		session.setAttribute("mvo", m_id);
		
		//MVC는 대부분 forward지만 새롭게 화면을 표현하고 싶을 대는 redirect 사용
		mv.setViewName("redirect:/"); //redirect안에 /는 IndexController에 있는 @RequestMapping("/")의 함수를 호출
		
		return mv;
	}
	
	
}

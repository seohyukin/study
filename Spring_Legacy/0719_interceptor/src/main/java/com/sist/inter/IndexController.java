package com.sist.inter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	//ModelAndView: jsp에서 표현하고 싶을 때
	//String: 경로 반환
	//Map: json(Key:Value)

	//value = "" 안에 있는 것은 기본 url(com.sist.inter) 다음의 경로다.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		
		return "index";
	}
	
	//value = "" 안에 있는 것은 기본 url(com.sist.inter) 다음의 경로다. (/배줘도 무방)
	@RequestMapping("/index")
	public String main() {
		return "index";
	}
	
	@RequestMapping("/sub/bravo")
	public ModelAndView bravo() {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("msg", "위드유"); //jsp에서 표현할 자원
		
		mv.setViewName("bravo"); //views에 있는 bravo.jsp를 의미
		
		return mv;
	}
}


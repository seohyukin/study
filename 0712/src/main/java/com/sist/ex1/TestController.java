package com.sist.ex1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.EmpDAO;
import mybatis.dao.MemDAO;
import mybatis.vo.EmpVO;
import mybatis.vo.MemVO;

@Controller
public class TestController {
	
	@Autowired
	private MemDAO m_dao; //DAO들을 톰캣이 구동될 때 미리 생성되므로 현재 객체가 만들어질 때는 이미 생성되어 있는 상태이다. 그러므로 Autowired로 자동 주입이 된다.
	
	@Autowired
	private EmpDAO e_dao;
	
	@RequestMapping("/t1")
	public ModelAndView test1() {
		ModelAndView mv = new ModelAndView();
		
		//뷰 페이지에서 표현할 자원들을 mv에 저장하자
		mv.addObject("str", "Spring MVC 테스트");
		mv.setViewName("test1"); //WEB-INF/views/test1.jsp를 의미함
		
		return mv;
	}
	
	@RequestMapping("/login")
	public String login() {
		return "ex6";
	}
	
	/*
		스프링 환경에서 Mybatis연결법
		필요한 라이브러리 목록 (MVN Repository)
		- spring jdbc
		- spring tx
		- commons dbcp
		- commons pool
		- commons logging
		- mybatis
		- mybatis spring
		
		위의 라이브러리들을 모두 pom.xml에 명시한 후 root-context.xml에 빈들을 추가해야 한다. (root-context.xml 문서 참조)
	*/
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public ModelAndView test(String id, String pw) {
		//사용자가 jsp에서 입력한 아이디와 비밀번호를 인자로 받는다.
		//DAO를 활용하여 DB로부터 검증하자!
		MemVO mvo = m_dao.login(id, pw);
		
		ModelAndView mv = new ModelAndView();
		if (mvo != null) {
			mv.addObject("mvo", mvo);
		}
		
		mv.setViewName("login_res");
		
		return mv;
	}
	
	@RequestMapping("/search")
	public String search() {
		return "emp1";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public ModelAndView num_search(String empno) {
		EmpVO evo = e_dao.emp(empno);
		
		ModelAndView mv = new ModelAndView();
		if (evo != null) {
			mv.addObject("evo", evo);
		}
		
		mv.setViewName("emp2");
		
		return mv;
	}
	
	@RequestMapping("/searchAll")
	public ModelAndView searchAll() {
		List<EmpVO> e_list = e_dao.empAll();
		
		ModelAndView mv = new ModelAndView();
		if (e_list != null) {
			mv.addObject("e_list", e_list);
		}
		
		mv.setViewName("emp2");
		
		return mv;
	}
}

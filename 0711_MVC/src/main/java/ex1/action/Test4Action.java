package ex1.action;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ex1.vo.ParamVO;

@Controller
public class Test4Action {
	
	@RequestMapping("/t4.inc") //요청 방식을 모를 때 사용 (get인지 post인지 애매할 때)
	public ModelAndView exe() {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("msg", "연습입니다.");
		
		mv.setViewName("ex4");
		
		return mv;
	}
	
	@RequestMapping("/t5.inc")
	public ModelAndView exe2() {
		ModelAndView mv = new ModelAndView();
		
		LocalDate now = LocalDate.now();
		
		mv.addObject("now", now.toString());
		
		mv.setViewName("ex5");
		
		return mv;
	}
	
	//단순히 jsp로 이동하고 싶을 때는 String을 반환하자 (ModelAndView, Request에 아무 것도 저장하지 않고 jsp로 이동)
	@RequestMapping("/t6.inc")
	public String exe3() {
		return "ex6";
	}
	
	@RequestMapping("/t7.inc")
	public ModelAndView exe4(String id, String pw) { //ex6.jsp에 form 안에 action이 "t7.inc"인 input text박스의 name을 인자로 받는다.
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("m_id", id);
		mv.addObject("m_pw", pw);
		
		mv.setViewName("ex7");
		
		return mv;
	}
	
	@RequestMapping("/t8.inc")
	public String exe5() {
		return "ex8";
	}
	
	@RequestMapping("/t9.inc")
	public ModelAndView exe6(String c_idx, String u_name, String unit, String[] hobby) {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("c_idx", c_idx);
		mv.addObject("u_name", u_name);
		mv.addObject("unit", unit);
		mv.addObject("hobby", hobby);
		
		mv.setViewName("ex9");
		
		return mv;
	}
	
	@RequestMapping("/t10.inc")
	public ModelAndView exe7(ParamVO pvo) {
		ModelAndView mv = new ModelAndView();
		
		mv.addObject("vo", pvo);
		
		mv.setViewName("ex10");
		
		return mv;
	}
}

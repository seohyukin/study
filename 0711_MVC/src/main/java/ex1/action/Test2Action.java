package ex1.action;

import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

//상속이 아니고 컨트롤러라는 인터페이스를 구현해서 만든 비즈니스 컨트롤러
public class Test2Action implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//Request에 저장할 현재날짜를 구하자! (calendar방식은 제일 불편하므로 사용 권장 X)
		//Date now = new Date(System.currentTimeMillis());
		LocalDate now = LocalDate.now();
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("now", now.toString());
		
		//뷰 페이지 지정
		mv.setViewName("ex2"); //WEB-INF/jsp/ex2.jsp를 의미
		
		return mv;
	}

}

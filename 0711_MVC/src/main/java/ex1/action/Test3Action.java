package ex1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class Test3Action implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String v1 = request.getParameter("v1");
		
		String str = "회원";
		if (v1 != null) { //파라미터가 넘어왔을 때
			if (v1.equalsIgnoreCase("admin")) {
				str = "관리자";
			}
		}
		
		//반환 객체 생성
		ModelAndView mv = new ModelAndView();
		mv.addObject("str", str);
		
		//뷰 페이지 지정
		mv.setViewName("ex3");
		return mv;
	}

}

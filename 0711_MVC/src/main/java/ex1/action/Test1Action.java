package ex1.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

//상속을 받는 비즈니스 컨트롤러
public class Test1Action extends AbstractController {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//jsp에서 표현할 정보를 request에 저장 (앞으로 많이 안 쓰고 mv에 필요한 정보를 저장하는 방법을 많이 활용할 것)
		request.setAttribute("msg", "환영합니다.");
		
		//반환 객체 생성
		ModelAndView mv = new ModelAndView();
		
		//mv에 필요한 정보를 저장 -> jsp에서 활용 가능
		mv.addObject("str", "Spring MVC 연습");
		//위는 request에 저장되었다. (ModelAndView가 request에 저장되어 있는 놈을 가져온 것)
		
		//forward할 jsp를 ModelAndView에 지정한다.
		mv.setViewName("ex1"); //WEB-INF/jsp/ex1.jsp를 의미함 (Controller-servlet에 View Resolver에 의해 설정된 것)
		
		return mv;
	}

}

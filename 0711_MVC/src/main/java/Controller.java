


import javax.servlet.annotation.WebServlet;

import org.springframework.web.servlet.DispatcherServlet;

//프론트 컨트롤러
@WebServlet("*.inc") /*확장자가 inc로 끝나는 모든 것*/
public class Controller extends DispatcherServlet {
	//현재 컨드롤러는 WEB-INF/{컨트롤러명}-servlet.xml과 연동되어 있다.
	//DispatcherServlet이 Controller라는 이름으로 된 파일 안에 모든 bean객체를 로드함
	//jsp에서 BeanFactory bf = new ClassPathXmlApplicationContext("config.xml")을 해줄 필요 X
}

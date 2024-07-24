package spring.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//인터셉터는 상속을 받아서 만듬
public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//반환값 준비
		boolean res = true;
		
		//로그인 체크를 해서 만약 로그인이 안 된 상태이면 res에 false를 저장한다.
		//먼저 로그인 체크를 하기 위해 HttpSession을 얻어내자 (참고로 Intercepter는 Controller가 아니므로 세션이 내장객체로 있지 않아 autowired를 사용하지 못함)
		HttpSession session = request.getSession();
		//getSession(true): 만약에 세션이 삭제된 상태라면 새로운 세션을 생성 O
		//-> session에 null값 확인 불필요(true를 넣어주면 밑에 session을 쓸 때 null값이면 오류가 나는 상황을 방지 가능)
		//getSession(false): 만약에 세션이 삭제된 상태여도 새로운 세션을 생성 X 
		//-> session에 null값 확인 필요(빡세게 확인할 때)
		
		//예를 들어 로그인 시 세션에 "mvo"를 저장했다고 가정을 하면 여기서는 session에서 "mvo"를 얻어내면 된다.
		Object obj = session.getAttribute("mvo");
		if (obj == null) {
			response.sendRedirect("/login");
			res = false;
		}
		return res; 
		//true를 반환하면 원래 가려고 했던 경로로 진행을 계속하지만,
		//false이면 원래 경로로 진행하지 못한다.
	}	
}

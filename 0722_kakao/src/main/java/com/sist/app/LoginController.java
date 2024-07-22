package com.sist.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.vo.MemberVO;

@Controller
public class LoginController {

	@Autowired
	private HttpSession session;
	
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("login/kakao")
	public ModelAndView login(String code) {
		//인자는 카카오서버로부터 받는 값이다. (1번째 요청: 인증코드)
		
		ModelAndView mv = new ModelAndView();
		
		//카카오서버가 인자로 전달해 준 "인증코드"가 code라는 변수에 있다.
		//System.out.println("code: " + code);
		
		//받은 인증코드를 가지고 카카오서버와 통신을 하기 위해 토큰을 요청하여 받아내야 한다. (2번째 요청: 토큰)
		String access_Token = "";
		String refresh_Token = "";
		
		String req_url = "https://kauth.kakao.com/oauth/token";
		
		try {
			//웹 상의 경로를 객체화 시킨다.
			URL url = new URL(req_url);
			
			//웹 상의 경로와 연결한다.
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			//카카오서버 쪽에서 POST방식의 요청을 원하므로 method를 POST로 지정해야 한다.
			conn.setRequestMethod("POST");
			conn.setDoOutput(true); //POST로 지정했을 때 필수로 해줘야 하는 것
			
			//전달하고자 하는 파라미터들을 보낼 OutputStream 생성
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			
			//카카오 API 문서에 있는 4개의 파라미터를 정의하기 위해 문자열 편집이 필요하다.
			//ex) grant_type=authorization_code&client_id=...
			StringBuffer sb = new StringBuffer();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=672f558f632f4de78d77c876656bd3b5");
			sb.append("&redirect_uri=http://localhost:8080/login/kakao");
			sb.append("&code=");
			sb.append(code);
			
			bw.write(sb.toString()); //준비된 파라미터들을 카카오서버로 보낸다.
			bw.flush();
			
			//카카오 서버에 요청을 보낸 후 응답 결과가 성공적인 경우(200)에만 토큰들을 받아내야 한다. 
			int res_code = conn.getResponseCode();
			//System.out.println("res_code: " + res_code);
			
			if (res_code == 200) {
				//요청을 통해 얻은 JSON타입의 결과메시지를 읽어온다.
				//그 결과를 받기 위해 스트림 생성
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				StringBuffer result = new StringBuffer();
				//한 줄 단위로 읽어서 result라는 StringBuffer에 적재한다.
				String line = null;
				
				while((line = br.readLine()) != null) {
					result.append(line);
				}
				
				//System.out.println("result: " + result.toString());
				
				//JSON파싱 처리: "access_token"과 "refresh_token"을 찾아 값을 얻어내야 한다.
				JSONParser pars = new JSONParser();
				//위 객체는 mvnrepository에서 json-simple로 검색하여 첫 번째로 나오는 구글의 라이브러리애 았는 파서를 통해 문자열로 되어 있는 JSON표기법을 객체로 만들어 준다.
				Object obj = pars.parse(result.toString());
				JSONObject json = (JSONObject) obj;
				
				access_Token = (String) json.get("access_token");
				refresh_Token = (String) json.get("refresh_token");
				
				//System.out.println("a_token: " + access_Token);
				//System.out.println("r_token: " + refresh_Token);
				
				//이제 받은 토큰을 가지고 마지막 3번째 호출인 사용자 정보를 요청해야 한다.
				String apiURL = "https://kapi.kakao.com/v2/user/me";
				String header = "Bearer " + access_Token;
				
				//자바에서 특정 웹 상의 경로(URL)를 호출하기 위해서는 먼저 URL객체를 생성해야 한다.
				URL url2 = new URL(apiURL);
				
				HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
				
				conn2.setRequestMethod("POST");
				conn2.setDoOutput(true);
				
				//카카오 API의 문서 상에 조건이 access토큰을 header에 담아 보내라고 명시되어 있으므로 header설정을 여기서 해야 한다.
				conn2.setRequestProperty("Authorization", header);
				
				res_code = conn2.getResponseCode();
				//System.out.println("res_code: " + res_code);
				//System.out.println("Http..: " + HttpURLConnection.HTTP_OK);
				
				if (res_code == HttpURLConnection.HTTP_OK) {
					//요청이 성공한 경우 ('res_code == 200'과 같은 의미이나 조금 더 자바스러운 코드)
					
					//카카오 서버 쪽에서 사용자의 정보를 보냈다. 이것을 읽어서 필요한 정보들을 선별해야 한다.
					BufferedReader brdm = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
					
					String str = null;
					StringBuffer res = new StringBuffer();
					
					while ((str = brdm.readLine()) != null) {
						res.append(str); //카카오 서버에서 전달되는 모든 값들이 res에 누적된다.
					}
					//System.out.println("res: " + res.toString());
					
					//받은 JSON표기법의 문자열 값을 JSON객체로 변환 후 원하는 정보(nickname, profile_image)를 얻어낸다.
					//앞서 이미 paser객체(JSONParser pars)가 생성되어 있다.
					obj = pars.parse(res.toString());
					
					//위의 obj를 JSONObject로 변환해야 한다.
					json = (JSONObject) obj;
					
					//원하는 정보인 nickname과 profile_image가 있는 properties라는 키의 값을 얻어낸다.
					JSONObject props = (JSONObject) json.get("properties");
					
					String nickName = (String) props.get("nickname");
					String p_img = (String) props.get("profile_image");
					
					//System.out.println("nickName: " + nickName);
					//System.out.println("profile_image: " + profile_image);
					
					MemberVO mvo = new MemberVO();
					mvo.setNickName(nickName);
					mvo.setP_img(p_img);
					
					//이렇게 카카오에서 얻은 정보를 mvo에 저장한 후 ModelAndView에 저장하자
					mv.addObject("mvo", mvo);
					mv.setViewName("reg2");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	@RequestMapping("registry")
	public ModelAndView registry(MemberVO mvo) {
		ModelAndView mv = new ModelAndView();
		
		return mv;
	}
}

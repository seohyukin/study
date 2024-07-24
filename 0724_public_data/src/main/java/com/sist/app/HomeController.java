package com.sist.app;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import data.vo.DataVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws Exception { //try~catch씌우면 url이 지역변수가 되므로 그냥 함수에 throws Exception 사용
		
		URL url = new URL("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?serviceKey=KcZsFq6ToscMBynFsPwEITk6G3idWHmZq9RM4r8LLTiSF%2BXV5WGPWMMiu%2F2gac8wUPX1pVlTbfdYr3Sny%2FDKHQ%3D%3D&returnType=xml&numOfRows=1&pageNo=1&stationName="
							+ URLEncoder.encode("강남구", "utf-8") + "&dataTerm=DAILY&ver=1.0");
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		//서버로부터 받는 자원이 xml문서이므로 헤더에서 mimeType(≒contentType)을 지정한다.
		conn.setRequestProperty("Content-Type", "application/xml");
		
		conn.connect();
		
		//이제 요청하여 받을 자원들을 처리할 객체를 준비하자
		//SAX파서: 다시 로드할 때 루트부터 시작 (속도가 빠르지만 재접근할 때 느림)
		//DOM파서: 객체를 트리 구조로 만들어 놓고 다시 (재접근할 때 좋지만 속도가 느림)
		//JDON파서: SAX+DOM(SAX빌더가 로드만 빠르게 해주고 DOM파서의 장점을 살려 document를 만듬)
		SAXBuilder builder = new SAXBuilder();
		//이 객체를 사용하기 위해 MVNRepository.com에서 jdom으로 검색 -> 버전: 2.0.6을 받아서 pom.xml에 의존성 추가
		
		Document doc = builder.build(conn.getInputStream());
		
		//루트엘리먼트를 얻기
		Element root = doc.getRootElement(); //response라는 루트
		
		//루토의 자식들 중 body라는 요소 얻기 -> body 안에 있는 items라는 요소 얻기 -> items 안에 있는 item을 리스트로 얻기
		List<Element> list = root.getChild("body").getChild("items").getChildren("item");
		
//		Element body = root.getChild("body");
//		Element items = body.getChild("items");
//		List<Element> list = items.getChildren("item");
		
		//위의 list에 저장된 Element를 DataVO로 변환하기 위해 저장소를 준비하기
		ArrayList<DataVO> data_list = new ArrayList<DataVO>();
		
		DataVO d_vo = new DataVO();
		for (Element e : list) {
			//list로부터 얻어낸 e는 item요소다.
			String pm10Value = e.getChildText("pm10Value");
			String pm10Grade = e.getChildText("pm10Grade");
			String coValue = e.getChildText("coValue");
			String coGrade = e.getChildText("coGrade");
			String khaiValue = e.getChildText("khaiValue");
			String khaiGrade = e.getChildText("khaiGrade");
			String dataTime = e.getChildText("dataTime");
			
			//위의 각 자원들을 하나의 객체(DataVO)에 저장하자.
			d_vo.setPm10Value(pm10Value);
			d_vo.setPm10Grade(pm10Grade);
			d_vo.setCoValue(coValue);
			d_vo.setCoGrade(coGrade);
			d_vo.setKhaiValue(khaiValue);
			d_vo.setKhaiGrade(khaiGrade);
			d_vo.setDataTime(dataTime);

			data_list.add(d_vo);
		}
		
		//view페이지인 home.jsp에서 사용할 수 있도록 저장하기
		model.addAttribute("d_list", data_list);
		
		return "home"; //views/home.jsp를 의미
	}
	
}

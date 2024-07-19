package com.sist.jdom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import data.vo.MemberVO;

@Controller
public class MemberController {

	//오픈 API 서비스의 URL같은 경로가 멤버변수로 선언되어야 하지만 우리는 내부에 저장된 XML파일을 접근하는 형식이므로 절대경로를 얻을 수 있는 객체가 필요하다.
	@Autowired
	private ServletContext application;
	
	//반환형이 ModelAndView와 같다. (그냥 좀 더 직관적인 방식이자 옛날 방식)
	@RequestMapping("test")
	public String test(Model model) throws Exception {
		
		//준비된 XML문서의 절대경로를 얻어내자.
		String realPath = application.getRealPath("/resources/pm/data/member.xml");
		
		//XML문서를 읽기 위해 필요한 트리 구조의 객체 생성
		SAXBuilder builder = new SAXBuilder();
		
		//위에서 만든 builder를 통해 지정된 XML문서를 읽어 문서화(document)시킨다.
		Document doc = builder.build(realPath);
		
		//메모리 상에 존재하는 XML문서의 루트를 얻어낸다.
		Element root = doc.getRootElement(); //members
		System.out.println("ROOT:" + root.getName());
		
		//루트 엘리먼트의 자식들 중 태그명이 member인 자식들을 모두 가져오기
		List<Element> list = root.getChildren("member");

		//위의 list의 길이만큼 MemberVO 배열을 만들자
		MemberVO[] m_ar = null;
		
		if (list != null && list.size() > 0) {
			m_ar = new MemberVO[list.size()];
			
			//list가 Element타입이고 m_ar은 MemberVO타입이므로 list.toarray가 안된다.
			int i = 0;
			for (Element mem : list) {
				String name = mem.getChildText("name"); //마루치
				String email = mem.getChildText("email");
				String phone = mem.getChildText("phone");
				
				MemberVO mvo = new MemberVO();
				mvo.setName(name);
				mvo.setEmail(email);
				mvo.setPhone(phone);
				
				//생성된 mvo를 배열에 저장
				m_ar[i++] = mvo;
			}
		}
		model.addAttribute("m_ar", m_ar);
		return "test";
	}
	
	@RequestMapping("test/search")
	@ResponseBody
	public Map<String, Object> search(String searchType, String searchValue) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		//준비된 XML문서의 절대경로를 얻어내자.
		String realPath = application.getRealPath("/resources/pm/data/member.xml");
		
		//XML문서를 읽기 위해 필요한 트리 구조의 객체 생성
		SAXBuilder builder = new SAXBuilder();
		
		//위에서 만든 builder를 통해 지정된 XML문서를 읽어 문서화(document)시킨다.
		Document doc = builder.build(realPath);
		
		//메모리 상에 존재하는 XML문서의 루트를 얻어낸다.
		Element root = doc.getRootElement(); //members
		
		System.out.println("sv:" + searchValue);
		
		//루트 엘리먼트의 자식들 중 태그명이 member인 자식들을 모두 가져오기
		List<Element> list = root.getChildren("member");
		
		//위의 list의 길이만큼 MemberVO 배열을 만들자
		MemberVO[] m_ar = null;
		
		if (list != null && list.size() > 0) {
			m_ar = new MemberVO[list.size()];
			//list가 Element타입이고 m_ar은 MemberVO타입이므로 list.toarray가 안된다.
			int i = 0;
			for (Element mem : list) {
				MemberVO mvo = new MemberVO();
				
				String name = mem.getChildText("name"); //마루치
				String email = mem.getChildText("email");
				String phone = mem.getChildText("phone");
				
				if (searchType.equals("0") && name.contains(searchValue)) { //이름
					mvo.setName(name);
					mvo.setEmail(email);
					mvo.setPhone(phone);
				} else if (searchType.equals("1") && email.contains(searchValue)) { //이메일
					mvo.setName(name);
					mvo.setEmail(email);
					mvo.setPhone(phone);
				} else if (searchType.equals("2") && phone.contains(searchValue)) { //연락처
					mvo.setName(name);
					mvo.setEmail(email);
					mvo.setPhone(phone);
				}
				//생성된 mvo를 배열에 저장
				m_ar[i++] = mvo;
			}
		}
		map.put("m_ar", m_ar);
		return map;
	}
}
	
				
		

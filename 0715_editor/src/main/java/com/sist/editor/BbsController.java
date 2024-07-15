package com.sist.editor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.vo.ImgVO;

@Controller
public class BbsController {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ServletContext application;
	
	@RequestMapping("/list")
	private ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		
		//표현할 목록 가져오기
		BbsVO[] ar = b_dao.getList("BBS", 1, 10);
		
		mv.addObject("ar", ar);
		
		mv.setViewName("list");
		
		return mv;
	}
	
	@RequestMapping("/write")
	public String write() {
		return "write";
	}
	
	@RequestMapping(value = "saveImg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveImg(ImgVO ivo) {
		//반환객체 생성
		Map<String, String> map = new HashMap<String, String>();
		
		//전달되어 오는 이미지를 ivo에서 가져온다.
		MultipartFile f = ivo.getFile();
		if (f.getSize() > 0) {
			//파일이 있는 경우
			//파일을 저장할 위치를 절대경로로 만들어야 한다.
			String realPath = application.getRealPath("/resources/editor_img");
			
			//전달된 파일을 저장한다.
			try {
				//파일 올리기
				f.transferTo(new File(realPath, f.getOriginalFilename()));
				map.put("fname", f.getOriginalFilename());
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		//현재 파일이 저장된 서버경로를 문자열로 만들자.
		//ex) localhost:8080/editor/editor_img/test.png
		String c_path = request.getContextPath();
		map.put("url", c_path+"/resources/editor_img");
		
		return map; //요청한 곳으로 보내진다. 이 때 JSON으로 보내기 위해 현재 메서드 위에 ResponseBody를 지정한다.
	}
}

package com.sist.bbs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import bbs.util.FileRenameUtil;
import bbs.util.Paging2;
import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import mybatis.vo.CommVO;

@Controller
public class BbsController {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ServletContext application;
	
	@Autowired
	private HttpSession session;
	
	//썸머노트 데이터에서 이미지를 추가할 때 이미지파일이 저장될 위치 
	private String editor_img = "/resources/editor_img";
	
	//글쓰기 할 때 첨부파일을 저장할 위치
	private String upload = "/resources/upload";
	
	int nowPage; //현재 페이지
	int numPerPage = 3; //한 페이지당 보여질 게시물 수
	int pagePerBlock = 3; //한 블럭당 보여질 페이지 수
	int totalRecord; //총 게시물 수
	
	@RequestMapping("/list")
	public ModelAndView list(String cPage, String bname) {
		ModelAndView mv = new ModelAndView();
		
		if (bname == null) {
			bname = "bbs";
		}
		
		//인자로 넘어온 파라미터가 현재페이지 값이며 이 값이 null일 때 기본적으로 첫 번째 페이지가 지정되어야 한다.
		if (cPage == null) {
			nowPage = 1;
		} else {
			nowPage = Integer.parseInt(cPage);
		}
		
		//페이징 기법
		totalRecord = b_dao.getCount(bname);
		
		//페이징 객체 생성
		Paging2 page = new Paging2(nowPage, totalRecord, numPerPage, pagePerBlock);
		
		int begin = page.getBegin(); 
		int end = page.getEnd();
		 
		BbsVO[] ar = b_dao.getList(bname, begin, end);
		
		//뷰 페이지에서 표현할 수 있도록 mv에 저장하자 (page객체로 묶어서 보내지 않고 하나씩 저장하면 jsp에서 표현할 때 page.totalRecord가 아니라 totalRecord만 쓰면 됨)
		mv.addObject("ar", ar);
		mv.addObject("totalRecord", totalRecord);
		mv.addObject("nowPage", nowPage);
		mv.addObject("bname", bname);
		mv.addObject("numPerPage", numPerPage);
		mv.addObject("pageCode", page.getSb().toString()); //StringBuffer는 String이 아니므로 toString 처리를 해줘야 함
		
		mv.setViewName(bname + "/list"); //bbs/list.jsp를 의미
		
		return mv;
	}
	
	@RequestMapping("/write")
	public String write(String bname) {
		return bname+"/write";
	}
	
	@RequestMapping(value = "saveImg", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> saveImg(MultipartFile s_file) {
		//반환형 객체
		Map<String, String> map = new HashMap<String, String>();
		
		String fname = null;
		
		if (s_file != null && s_file.getSize() > 0) {
			//파일이 잘 들어온 경우
			//업로드할 위치(editor_img)를 절대경로화 시킴
			String realPath = application.getRealPath(editor_img);
			
			//파일명 얻기
			fname = s_file.getOriginalFilename();
			
			//파일명이 이미 있다면 파일명을 변경해야 한다.
			fname = FileRenameUtil.checkSameFileName(fname, realPath);
			
			try {
				//파일 업로드
				s_file.transferTo(new File(realPath, fname));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String c_path = request.getContextPath(); //서버경로
		//이미지 경로랑 이미지 파일명을 붙여서 한 번에 보내도 됨
		map.put("url", c_path+editor_img); //이미지 경로
		map.put("fname", fname); //이미지 파일명
		
		return map; //JSON표기법으로 변환되어 write.jsp의 sendImage함수의 done영역으로 보내진다.
	}
	
	@RequestMapping(value = "write", method = RequestMethod.POST)
	public ModelAndView write(BbsVO vo) {
		//폼 양식에서 첨부파일이 전달될 때 encType이 지정된다.
		String c_type = request.getContentType();
		if (c_type.startsWith("multipart")) { //"multipart"로 시작되었나 = 파일이 첨부 되었는가
			//파일이 첨부된 상태인지 아닌지 판단
			MultipartFile f = vo.getFile();
			String fname = null;
			if (f != null && f.getSize() > 0) {
				//첨부된 상태
				String realPath = application.getRealPath(upload);
				
				fname = f.getOriginalFilename();
				vo.setOri_name(fname); //원래 파일명
				
				fname = FileRenameUtil.checkSameFileName(fname, realPath);
				
				try {
					//파일 업로드(upload폴더에 저장)
					f.transferTo(new File(realPath, fname));
					vo.setFile_name(fname); //저장된 파일명
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			vo.setIp(request.getRemoteAddr()); //접속자의 ip
			
			//vo를 DAO에게 전달하여 DB에 저장하도록 한다.
			b_dao.add(vo);
		}
		ModelAndView mv = new ModelAndView();
		//list.jsp로 가는 게 아니라 BbsController 위에 있는 list함수를 수행한다.
		mv.setViewName("redirect:/list?bname=" + vo.getBname());
		
		return mv;
	}
	
	@RequestMapping("/view")
	public ModelAndView view(String b_idx, String bname, String cPage) {
		ModelAndView mv = new ModelAndView();
	
		List<BbsVO> list = null;
		
		//세션으로부터 이름이 r_list라는 이름으로 저장된 객체를 얻어낸다.
		Object obj = session.getAttribute("r_list");
		
		if (obj != null) {
			list = (List<BbsVO>)obj;
		} else {
			list = new ArrayList<BbsVO>();
			session.setAttribute("r_list", list); //list의 주소가 r_list라는 이름으로 session에 저장
		}
		
		//이제 list에서 인자로 받은 b_idx값과 같은 값을 가진 BbsVO를 검색한다. 만약 있다면 hit를 증가하면 안된다.
		boolean chk = false;
		
		for (BbsVO bvo : list) {
			if (bvo.getB_idx().equals(b_idx)) {
				chk = true;
				break;
			} 
		}
		
		if (!chk) { //chk가 false를 유지할 때
			b_dao.hit(b_idx); //조회수 증가
		}
		
		BbsVO vo = b_dao.getBbs(b_idx);
		
		if (vo != null) {
			if (!chk) {
				list.add(vo);
			}
			mv.addObject("vo", vo);			
		}
		mv.setViewName(bname + "/view");
		
		return mv;
	}
	
	@RequestMapping("/answer")
	public ModelAndView comm(CommVO cvo, String bname, String cPage) {
		cvo.setIp(request.getRemoteAddr());
		b_dao.addComm(cvo);
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("redirect:/view?bname=" + bname + "&cPage=" + cPage + "&b_idx=" + cvo.getB_idx());
		
		return mv;
	}
	
	@RequestMapping("/edit")
	public ModelAndView edit(BbsVO vo, String cPage) {
		ModelAndView mv = new ModelAndView();
		
		//어떤 jsp에서 호출했는지 구별하기 위해 contentType 받기
		//(post방식의 form에서 호출되어 왔다면 "application..."
		// get방식의 form에서 호출되어 왔다면 "null"
		// 파일이 첨부된 form에서 호출되어 왔다면 "multipart...")
		String c_type = request.getContentType();
		if (c_type.startsWith("app")) {
			//수정화면인 edit.jsp로 가도록 해야 함
			//먼저 화면에 보여질 게시물 객체를 찾아서 mv에 저장하자
			BbsVO bvo = b_dao.getBbs(vo.getB_idx());
			
			mv.addObject("vo", bvo);
			
			//bbs/edit.jsp로 forward됨
			mv.setViewName(vo.getBname() + "/edit");
		} else if (c_type.startsWith("multi")) {
			//첨부된 파일 처리 후 DB 수정 작업
			
			//파일이 첨부된 상태인지 아닌지 판단
			MultipartFile f = vo.getFile();
			String fname = null;
			System.out.println("파일: " + f); //Spring에서 f에 값을 알아서 넣어주므로 무조건 null이 아니다. 그러나 size는 0이다. 
			if (f != null && f.getSize() > 0) { //위와 같은 이유로 f != null은 항상 참이므로 무의미하다.
				//첨부된 상태
				String realPath = application.getRealPath(upload);
				
				fname = f.getOriginalFilename();
				vo.setOri_name(fname); //DB에 저장할 vo에 원래 파일명을 저장하자.
				
				//이미 존재하는 파일명이면 이름 변경
				fname = FileRenameUtil.checkSameFileName(fname, realPath);
				
				try {
					//파일 업로드(upload폴더에 저장)
					f.transferTo(new File(realPath, fname)); //업로드 완료
					vo.setFile_name(fname); //저장된 파일명
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			vo.setIp(request.getRemoteAddr()); //접속자의 ip
			
			//mybatis에게 vo를 주면서 수정하라고 하자
			b_dao.edit(vo);
				
			mv.setViewName("redirect:/view?b_idx=" + vo.getB_idx() + "&bname=" + vo.getBname() + "&cPage=" + cPage);
		}
		return mv;
	}
	
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public ModelAndView del(BbsVO vo) {
		ModelAndView mv = new ModelAndView();
		
		b_dao.del(vo.getB_idx());
		
		//list.jsp로 가는 게 아니라 BbsController 위에 있는 list함수를 수행한다.
		mv.setViewName("redirect:/list?bname=" + vo.getBname());
		
		return mv;
	}

}




package com.sist.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.sist.app.service.BbsService;
import com.sist.app.service.CommService;
import com.sist.app.util.FileRenameUtil;
import com.sist.app.util.Paging2;
import com.sist.app.vo.BbsVO;
import com.sist.app.vo.CommVO;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class BbsController {
    
    @Autowired
    private BbsService b_service;

    @Autowired
    private CommService c_service;

    @Autowired
    private ServletContext application;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpSession session;

    @Value("${server.upload.path}")
    private String upload; //"/upload"

    @Value("${server.editor_img.path}")
    private String editor_img;

    @RequestMapping("list")
    public ModelAndView list(@RequestParam String bname, String cPage, String searchType, String searchValue) {
        ModelAndView mv = new ModelAndView();

        int nowPage = 1;

        if (cPage != null) {
            nowPage = Integer.parseInt(cPage);
        }
        if (bname == null || bname.trim().length() == 0) {
            bname = "bbs"; //bbs도 upload처럼 properties에 변수 선언하면 유지보수가 더 수월하다.
        }

        //전체 게시물의 수
        int totalRecord = b_service.getCount(searchType, searchValue, bname);

        //위에서 전체 게시물의 수를 얻었으니 페이징 기법에 사용하는 객체를 생성할 수 있다.
        Paging2 page = new Paging2(nowPage, totalRecord, 7, 5, bname); //numPerPage와 pagePerBlock도 upload처럼 properties에 변수 선언하면 유지보수가 더 수월하다.

        nowPage = page.getNowPage();

        //페이징 기법에 HTML코드를 얻어내자.
        String pageCode = page.getSb().toString();

        //뷰페이지에서 표현할 목록 가져오기
        int begin = page.getBegin();
        int end = page.getEnd();
        BbsVO[] b_ar = b_service.getList(searchType, searchValue, bname, begin, end);

        mv.addObject("b_ar", b_ar);
        mv.addObject("len", b_ar.length);
        mv.addObject("page", page);
        mv.addObject("pageCode", pageCode); //사실 page를 저장했으면 pageCode는 저장 안 해도 되지만 편의상?연습상? 추가
        mv.addObject("totalRecord", totalRecord);
        mv.addObject("numPerPage", page.getNumPerPage());
        mv.addObject("bname", bname);
        mv.addObject("nowPage", nowPage);

        mv.setViewName(bname + "/list");

        return mv;
    }

    @GetMapping("write")
    public String write(String bname, String cPage) { //여기서 받은 인자들은 forward방식으로 넘겼으므로 jsp에서 ${param.인자}로 사용 가능
        return bname + "/write";
    }

    @PostMapping("write")
    public ModelAndView add(BbsVO bvo) {
        String c_type = request.getContentType();

        if (c_type.startsWith("multipart")) { //"multipart"로 시작되었나 = 파일이 첨부 되었는가
            //파일이 첨부된 상태인지 아닌지 판단 (파일이 첨부된 상태라면 bvo가 가지고 있으므로 bvo에서 파일을 가져온다.)
			MultipartFile f = bvo.getFile();
			String oname = null;
			if (f.getSize() > 0) { //파일이 첨부되지 않았다고 해도 f는 null이 아니면 용량으로 확인해야 함(f != null 불필요)
				//파일이 첨부된 상태일 때만 서버에 업로드

                //업로드할 서버의 위치를 절대경로화 시킨다.
				String realPath = application.getRealPath(upload);
				
				oname = f.getOriginalFilename();
				bvo.setOri_name(oname); //원래 파일명
				
                //이미 같은 이름의 파일이 있다면 파일명 변경하기
				String fname = FileRenameUtil.checkSameFileName(oname, realPath);
				
				try {
					//파일 업로드(upload폴더에 저장)
					f.transferTo(new File(realPath, fname));
					bvo.setFile_name(fname); //저장된 파일명
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
        }
        bvo.setIp(request.getRemoteAddr()); //접속자의 ip

        //vo를 BbsService에게 전달하여 DB에 저장하도록 한다.
        b_service.add(bvo);

        ModelAndView mv = new ModelAndView();

        mv.setViewName("redirect:/list?bname="+bvo.getBname());

        return mv;
    }

    @PostMapping("saveImg") //@RequestMapping도 상관없음
    @ResponseBody
    public Map<String, String> saveImg(MultipartFile s_file) { 
        //보내는 값이 문자열과 배열이 섞여 있으면 <String, Object>로 받지만 이번에는 경로만 보내면 되므로 <String, String>으로 받아도 된다.
        
        Map<String, String> map = new HashMap<String, String>();

        if (s_file.getSize() > 0) {
            //받은 파일을 저장할 editor_img를 절대경로화 시키자
            String realPath = application.getRealPath(editor_img);

            //넘어온 파일명을 얻어내자
            String oname = s_file.getOriginalFilename();

            //이미 같은 이름의 파일이 있다면 파일명 변경하기
            String fname = FileRenameUtil.checkSameFileName(oname, realPath);
            try {
                s_file.transferTo(new File(realPath, fname)); //업로드
            } catch (Exception e) {
                e.printStackTrace();
            }
            //업로드된 파일의 경로를 반환하기 위해 현재 서버에 URL을 알아내자
            String url_path = request.getContextPath();

            //JSON으로 반환하기 위해 map구조에 저장하자
            map.put("url", url_path + editor_img);
            map.put("fname", fname);
        }
        return map;
    }

    @RequestMapping("view")
    public ModelAndView view(String b_idx, String bname, String cPage) {
        ModelAndView mv = new ModelAndView();
	
		List<BbsVO> b_list = null;
		
		//세션에 r_list라는 이름으로 저장된 객체 얻기
		Object obj = session.getAttribute("r_list");
		
		if (obj != null) {
            b_list = (List<BbsVO>)obj;
		} else {
            b_list = new ArrayList<BbsVO>();
			session.setAttribute("r_list", b_list); //list의 주소가 r_list라는 이름으로 session에 저장
		}
		
        //사용자가 선택한 게시물의 기본 키를 b_list에서 인자로 받았으니 b_idx와 같은 값을 가진 BbsVO를 얻자. 
        BbsVO vo = b_service.getBbs(b_idx);
        
        //만약 있다면 hit를 증가하면 안된다. 이미 읽었던 게시물인지 확인
		boolean chk = false;
		for (BbsVO bvo : b_list) {
			if (bvo.getB_idx().equals(b_idx)) {
				chk = true;
				break;
			}
		}

        //chk가 false를 유지한다면 한 번도 읽지 않은 게시물이므로 hit수를 증가
		if (!chk) {
            //화면에 즉각적으로 반영하기 위해 먼저 hit값을 얻어내어 다시 vo에 저장해 둬야 한다.
            int hit = Integer.parseInt(vo.getHit());
            vo.setHit(String.valueOf(hit+1));

            //DB에서 hit수 증가
			b_service.hit(vo.getB_idx());

            //vo를 list에 저장
            b_list.add(vo);
		}
		mv.addObject("vo", vo);			
		mv.setViewName(bname + "/view");
		
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
			BbsVO bvo = b_service.getBbs(vo.getB_idx());
			
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
			b_service.edit(vo);
				
			mv.setViewName("redirect:/view?b_idx=" + vo.getB_idx() + "&bname=" + vo.getBname() + "&cPage=" + cPage);
		}
		return mv;
	}

    
    @RequestMapping(value = "del", method = RequestMethod.POST)
	public ModelAndView del(BbsVO vo) {
		ModelAndView mv = new ModelAndView();
		
        b_service.del(vo.getB_idx());
		
		mv.setViewName("redirect:/list?bname=" + vo.getBname());
		
		return mv;
	}

    @PostMapping("FileDownload")
    public ResponseEntity<Resource> fileDownload(String filename) {
        //파일들이 위치하는 곳(upload)을 절대경로화 시킨다.
        String realPath = application.getRealPath(upload + "/" + filename);
        File f = new File(realPath);

        if (f.exists()) {
            //파일이 실제 존재할 경우
            byte[] buf = new byte[4096];
            int size = -1;
            
            //다운로드에 필요한 스트림을 준비
            BufferedInputStream bis = null;
            FileInputStream fis = null;

            //보내기할 때 필요한 스트림을 준비
            BufferedOutputStream bos = null;
            //접속자의 컴퓨터로 다운로드를 시켜야 하기 때문에 response를 통해 OutputStream을 얻어내야 한다. 이 때 response가 주는 스트림이 ServletOutputStream밖에 없다.
            ServletOutputStream sos = null;
            
            try {
                //접속자 화면에 다운로드 창 보여주기
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "8859_1"));

                //다운로드할 파일과 연결되는 스트림
                fis = new FileInputStream(f);
                bis= new BufferedInputStream(fis);

                //response를 통해 이미 out이라는 스트림이 존재하므로 다운로드를 시키기 위해 스트림 준비
                sos = response.getOutputStream();
                bos = new BufferedOutputStream(sos);

                while ((size = bis.read(buf)) != -1) {
                    //읽은 자원을 buf에 적재된 상태다. 이제는 buf라는 배열에 있는 자원들을 쓰기해서 사용자에게 보낸다.
                    bos.write(buf, 0, size);
                    bos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    if (sos != null) {
                        bos.close();
                    }
                    if (bos != null) {
                        fis.close();
                    }
                } catch (Exception e) {}
            }
        }
        return null;
    }

    @RequestMapping("answer")
	public ModelAndView comm(CommVO cvo, String bname, String cPage) {
		cvo.setIp(request.getRemoteAddr());
		c_service.addComm(cvo);
		
		ModelAndView mv = new ModelAndView();
		
		mv.setViewName("redirect:/view?bname=" + bname + "&cPage=" + cPage + "&b_idx=" + cvo.getB_idx());
		
		return mv;
	}
}


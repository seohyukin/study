package com.sist.ex0726_secure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sist.ex0726_secure.service.BbsService;
import com.sist.ex0726_secure.vo.BbsVO;


@Controller
public class BbsController {
    
    //DB활용을 위한 service
    @Autowired
    private BbsService b_service;

    //아래 방식처럼 하고 "/upload"를 절대경로로 지정하는 방법을 그동안 해왔지만 보안에 취약하다. -> application.properties에서 설정하고 @Value로 가져오기
    //private String upload_file = "/upload";

    @RequestMapping("/bbs")
    public ModelAndView bbsList(@RequestParam String bname, String cPage, String searchType, String searchValue) {
        ModelAndView mv = new ModelAndView();
        int nowPage = 1;

        if(bname == null)
            bname = "bbs";

        //현재페이지 값인 cPage가 null이 아니면 사용자가 가고자 하는 페이지가 있으므로 nowPage값을 cPage로 변경하자
        if(cPage != null) {
            nowPage = Integer.parseInt(cPage);
        }

        //총 게시물의 수를 구해야 한다.
        int totalRecord = b_service.getCount(searchType, searchValue, bname);

        // Paging2객체 생성 후 페이징 HTML코드 생성까지 수행하는 객체 준비

        // 뷰페이지에서 보여질 게시물들을 얻어내어 mv에 저장한다.
        BbsVO[] b_ar = b_service.getList(searchType, searchValue, bname, "1", "10");

        mv.addObject("b_ar", b_ar);

        mv.setViewName(bname+"/list");

        return mv;
    }
    
}

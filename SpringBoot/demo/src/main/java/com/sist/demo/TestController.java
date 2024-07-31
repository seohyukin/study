package com.sist.demo;

import org.springframework.web.bind.annotation.RestController;

import test.vo.DataVO;
import test.vo.TestVO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TestController {
    //@RestController = @Controller + @ResponseBody

    private String msg; //msg에는 값이 아직 없으므로 null이다.

    @Value("${global.guestId}") //자동으로 application.properties에 있는 guestId값을 넣어준다. -> 고정시키고 싶은 문자열 값을 application.properties에 넣어놓기
    private String guestId;

    @Value("${global.siteName}")
    private String siteName;


    @GetMapping("/test")
    public String test() {
        return "Hello world";
    }

    //url경로를 변수 받는 것처럼 함
    @GetMapping("/test2/{var}")
    //public String varTest(@PathVariable("var") String str) { 경로와 변수명이 다르면 생략 불가
    public String varTest(@PathVariable String var) { //경로와 변수명이 같으면 생략 가능 (보통 인자가 1개 일 때 사용)
        return var;
    }

    @GetMapping("/req1")
    public String getReq(String name, String email) { //파라미터를 주지 않았을 때 null값이 찍힘
        //파라미터 받는 방법은 Spring과 같다.
        return name + "/" + email;
    }

    @GetMapping("/req2")
    public String getReq2(@RequestParam String name, @RequestParam String email) { //파라미터를 무조건 줘야 함 (순서 무관 | 보통 인자가 2개 이상 일 때 사용)
        return name + "/" + email + "/" + guestId + "/" + siteName;
    }
    
    @GetMapping("req3")
    public String getReq3() {
        TestVO tvo = new TestVO();

        tvo.setId(guestId);
        tvo.setName(siteName);

        return tvo.getId() + "/" + tvo.getName();
    }

    @GetMapping("req4")
    public String getReq4() {
        DataVO dvo = new DataVO(); //기본 생성자
        dvo.setMsg("반갑습니다.");

        return dvo.getMsg();
    }
    
}

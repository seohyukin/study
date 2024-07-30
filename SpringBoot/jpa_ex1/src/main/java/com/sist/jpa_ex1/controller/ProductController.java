package com.sist.jpa_ex1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sist.jpa_ex1.repository.ProductRepository;
import com.sist.jpa_ex1.store.ProductJPO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("test")
    public String test() {

        //생성자 방법
        //ProductJPO p1 = new ProductJPO(100L, "빈센트 아몬드나무", "Art company", null, 0, 0, 0, null);

        //빌드 패턴(디자인패턴 - 생성) -> 순서 맞출 필요 없음
        ProductJPO p2 = ProductJPO.builder()
                            .pNum(100L)
                            .pName("빈센트 아몬드나무")
                            .pCompany("Art company")
                            .build();

        //System.out.println(p1.getPNum() + ", " + p1.getPName() + ", " + p1.getPCompany());

        //productRepository.save(p1); //생성자 방법 저장
        productRepository.save(p2); //빌드 패턴 저장

        return "TEST";
    }

    @GetMapping("list")
    public String getList() {
        List<ProductJPO> p_list = productRepository.findAll();

        StringBuffer sb = new StringBuffer();
        for (ProductJPO p : p_list) {
            sb.append(p.getPNum());
            sb.append("/");
            sb.append(p.getPName());
            sb.append("/");
            sb.append(p.getCvo1().getCName());
            sb.append("/");
            sb.append(p.getCvo1().getDesc());
            sb.append("<br/>");
        }
        return sb.toString();
    }
    
    
}

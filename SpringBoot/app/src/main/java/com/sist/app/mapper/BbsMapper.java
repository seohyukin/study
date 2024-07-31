package com.sist.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sist.app.vo.BbsVO;

 @Mapper
public interface BbsMapper {
    //mybatis는 인자를 하나만 받지만 여러 개를 주면 내부적으로 map으로 묶어줌 (map으로 받는 것보다 무엇을 인자로 넣어야 하는지에 대해 직관성 높음)
    int count(String searchType, String searchValue, String bname);

    List<BbsVO> b_list(String searchType, String searchValue, String bname, int begin, int end);

    int add(BbsVO bvo);

    BbsVO getBbs(String b_idx);

    int hit(String b_idx);

    int edit(BbsVO bvo);

    int del(String b_idx);
}

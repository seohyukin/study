package com.sist.ex0726_secure.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.ex0726_secure.mapper.BbsMapper;
import com.sist.ex0726_secure.vo.BbsVO;

@Service
public class BbsService {
    
    @Autowired
    private BbsMapper mapper;

    public int getCount(String searchType, String searchValue, String bname){
            return mapper.count(searchType, searchValue, bname);
    }

    public BbsVO[] getList(String searchType, String searchValue,
        String bname, String begin, String end){
            BbsVO[] b_ar = null;

            List<BbsVO> b_list = mapper.list(searchType, searchValue, bname, begin, end);
            if(b_list != null && b_list.size() > 0){
                b_ar = new BbsVO[b_list.size()];
                b_list.toArray(b_ar);
            }

            return b_ar;
    }
}

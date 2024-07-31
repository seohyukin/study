package com.sist.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.app.mapper.BbsMapper;
import com.sist.app.vo.BbsVO;

@Service
public class BbsService {
    @Autowired
    private BbsMapper b_mapper;

    public int getCount(String searchType, String searchValue, String bname){
            return b_mapper.count(searchType, searchValue, bname);
    }

    public BbsVO[] getList(String searchType, String searchValue, String bname, int begin, int end){
            BbsVO[] b_ar = null;

            List<BbsVO> b_list = b_mapper.b_list(searchType, searchValue, bname, begin, end);
            if(b_list != null && b_list.size() > 0){
                b_ar = new BbsVO[b_list.size()];
                b_list.toArray(b_ar);
            }
            return b_ar;
    }

    public int add(BbsVO bvo) {
        return b_mapper.add(bvo);
    }

	public BbsVO getBbs(String b_idx) {
		return b_mapper.getBbs(b_idx);
	}
	
	public int hit(String b_idx) {
		return b_mapper.hit(b_idx);
	}
	
	public int edit(BbsVO bvo) {
		return b_mapper.edit(bvo);
	}
	
	public int del(String b_idx) {
		return b_mapper.del(b_idx);
	}

}

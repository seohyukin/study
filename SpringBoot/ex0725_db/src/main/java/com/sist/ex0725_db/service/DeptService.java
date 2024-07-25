package com.sist.ex0725_db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.ex0725_db.mapper.DeptMapper;
import com.sist.ex0725_db.vo.DeptVO;

@Service
public class DeptService {
    
    @Autowired
    private DeptMapper mapper;

    public DeptVO[] getAll() {
        DeptVO[] d_ar = null;

        List<DeptVO> d_list = mapper.all();

        if (d_list != null && d_list.size() > 0) {
            d_ar = new DeptVO[d_list.size()];
            d_list.toArray(d_ar);
        }
        return d_ar;
    }
}

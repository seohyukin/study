package com.sist.ex0725_db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.ex0725_db.mapper.EmpMapper;
import com.sist.ex0725_db.vo.EmpVO;

@Service
public class EmpService {
    //필요한 맵퍼 선언
    @Autowired
    private EmpMapper mapper;

    public EmpVO[] getAll() {
        EmpVO[] e_ar = null;

        List<EmpVO> e_list = mapper.all();
        if (e_list != null && !e_list.isEmpty()) {
            e_ar = new EmpVO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

    public EmpVO[] getList(String begin, String end) {
        EmpVO[] e_ar = null;

        List<EmpVO> e_list = mapper.list(begin, end);
        if (e_list != null && !e_list.isEmpty()) {
            e_ar = new EmpVO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

    //비동기식 X
    public EmpVO[] search(String searchType, String searchValue) {
		EmpVO[] e_ar = null;

		List<EmpVO> e_list = mapper.search(searchType, searchValue);

		if (e_list != null && e_list.size() > 0) {
			e_ar = new EmpVO[e_list.size()];

			e_list.toArray(e_ar);
		}
		return e_ar;
	}

    //비동기식 O
    public EmpVO[] search_ajax(String searchType, String searchValue) {
        EmpVO[] e_ar = null;

        Map<String, String> map = new HashMap<String,String>();

        map.put("searchType", searchType);
        map.put("searchValue", searchValue);

        List<EmpVO> e_list = mapper.search(searchType, searchValue);
        if (e_list != null && e_list.size() > 0) {
            e_ar = new EmpVO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

}

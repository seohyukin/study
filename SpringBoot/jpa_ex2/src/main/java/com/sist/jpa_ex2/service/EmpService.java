package com.sist.jpa_ex2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sist.jpa_ex2.repository.EmpRepository;
import com.sist.jpa_ex2.store.EmpJPO;

@Service
public class EmpService {
    @Autowired
    private EmpRepository empRepository;

    public EmpJPO[] getAll() {
        EmpJPO[] e_ar = null;

        List<EmpJPO> e_list = empRepository.findAll();
        if (e_list != null && e_list.size() > 0) {
            e_ar = new EmpJPO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

    public EmpJPO[] searchName(String ename) {
        EmpJPO[] e_ar = null;

        List<EmpJPO> e_list = empRepository.findByEname(ename);
        if (e_list != null && e_list.size() > 0) {
            e_ar = new EmpJPO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

    public EmpJPO[] searchNameContain(String ename) {
        EmpJPO[] e_ar = null;

        List<EmpJPO> e_list = empRepository.findByEnameContaining(ename);
        if (e_list != null && e_list.size() > 0) {
            e_ar = new EmpJPO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

    //findByEnameLike는 %를 넣어줘야 된다.
    public EmpJPO[] searchNameLike(String ename) {
        EmpJPO[] e_ar = null;

        List<EmpJPO> e_list = empRepository.findByEnameLike("%" + ename + "%");
        if (e_list != null && e_list.size() > 0) {
            e_ar = new EmpJPO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

    public EmpJPO[] searchJobContain(String job) {
        EmpJPO[] e_ar = null;

        List<EmpJPO> e_list = empRepository.findByJobContains(job);
        if (e_list != null && e_list.size() > 0) {
            e_ar = new EmpJPO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

    public EmpJPO[] searchJobAndDeptno(String job, String deptno) {
        EmpJPO[] e_ar = null;

        List<EmpJPO> e_list = empRepository.findByJobContainsAndDeptno(job, deptno);
        if (e_list != null && e_list.size() > 0) {
            e_ar = new EmpJPO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

    public EmpJPO[] searchJobOrDeptno(String job, String deptno) {
        EmpJPO[] e_ar = null;

        List<EmpJPO> e_list = empRepository.findByJobContainsOrDeptno(job, deptno);
        if (e_list != null && e_list.size() > 0) {
            e_ar = new EmpJPO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }

    public EmpJPO[] searchSal(int sal) {
        EmpJPO[] e_ar = null;

        List<EmpJPO> e_list = empRepository.findBySalLessThanEqual(sal);
        if (e_list != null && e_list.size() > 0) {
            e_ar = new EmpJPO[e_list.size()];
            e_list.toArray(e_ar);
        }
        return e_ar;
    }
}

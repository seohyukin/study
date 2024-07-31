package com.sist.jpa_ex2.controller;

import org.springframework.web.bind.annotation.RestController;

import com.sist.jpa_ex2.service.EmpService;
import com.sist.jpa_ex2.store.EmpJPO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class EmpController {
    
    @Autowired
    private EmpService e_service;

    @GetMapping("all")
    public String all() {
        StringBuffer sb = new StringBuffer();

        EmpJPO[] e_ar = e_service.getAll();
        for (EmpJPO emp : e_ar) {
            sb.append(emp.getEmpno());
            sb.append("/");
            sb.append(emp.getEname());
            sb.append("</br>");
        }
        return sb.toString();
    }

    @GetMapping("search_name")
    public String search_name(String ename) {
        StringBuffer sb = new StringBuffer();

        EmpJPO[] e_ar = e_service.searchName(ename);
        if (e_ar != null) {
            for (EmpJPO emp : e_ar) {
                sb.append(emp.getEmpno());
                sb.append("/");
                sb.append(emp.getEname());
                sb.append("</br>");
            }
        } else {
            sb.append("없음");
        }
        return sb.toString();
    }

    @GetMapping("search_name_contain")
    public String search_name_contain(String ename) {
        StringBuffer sb = new StringBuffer();

        EmpJPO[] e_ar = e_service.searchNameContain(ename);
        if (e_ar != null) {
            for (EmpJPO emp : e_ar) {
                sb.append(emp.getEmpno());
                sb.append("/");
                sb.append(emp.getEname());
                sb.append("</br>");
            }
        } else {
            sb.append("없음");
        }
        return sb.toString();
    }

    @GetMapping("search_name_like")
    public String search_name_like(String ename) {
        StringBuffer sb = new StringBuffer();

        EmpJPO[] e_ar = e_service.searchNameLike(ename);
        if (e_ar != null) {
            for (EmpJPO emp : e_ar) {
                sb.append(emp.getEmpno());
                sb.append("/");
                sb.append(emp.getEname());
                sb.append("</br>");
            }
        } else {
            sb.append("없음");
        }
        return sb.toString();
    }

    @GetMapping("search_job_contain")
    public String search_job_contain(String job) {
        StringBuffer sb = new StringBuffer();

        EmpJPO[] e_ar = e_service.searchJobContain(job);
        if (e_ar != null) {
            for (EmpJPO emp : e_ar) {
                sb.append(emp.getEmpno());
                sb.append("/");
                sb.append(emp.getEname());
                sb.append("/");
                sb.append(emp.getJob());
                sb.append("</br>");
            }
        } else {
            sb.append("없음");
        }
        return sb.toString();
    }

    @GetMapping("search_job_deptno")
    public String search_job_deptno(String job, String deptno) {
        StringBuffer sb = new StringBuffer();

        EmpJPO[] e_ar = e_service.searchJobAndDeptno(job, deptno);
        if (e_ar != null) {
            for (EmpJPO emp : e_ar) {
                sb.append(emp.getEmpno());
                sb.append("/");
                sb.append(emp.getEname());
                sb.append("/");
                sb.append(emp.getJob());
                sb.append("/");
                sb.append(emp.getDeptno());
                sb.append("</br>");
            }
        } else {
            sb.append("없음");
        }
        return sb.toString();
    }

    @GetMapping("search2_job_deptno")
    public String search2_job_deptno(String job, String deptno) {
        StringBuffer sb = new StringBuffer();

        EmpJPO[] e_ar = e_service.searchJobOrDeptno(job, deptno);
        if (e_ar != null) {
            for (EmpJPO emp : e_ar) {
                sb.append(emp.getEmpno());
                sb.append("/");
                sb.append(emp.getEname());
                sb.append("/");
                sb.append(emp.getJob());
                sb.append("/");
                sb.append(emp.getDeptno());
                sb.append("</br>");
            }
        } else {
            sb.append("없음");
        }
        return sb.toString();
    }

    @GetMapping("search_sal")
    public String search_sal(int sal) {
        StringBuffer sb = new StringBuffer();

        EmpJPO[] e_ar = e_service.searchSal(sal);
        if (e_ar != null) {
            for (EmpJPO emp : e_ar) {
                sb.append(emp.getEmpno());
                sb.append("/");
                sb.append(emp.getEname());
                sb.append("/");
                sb.append(emp.getJob());
                sb.append("/");
                sb.append(emp.getDeptno());
                sb.append("/");
                sb.append(emp.getSal());
                sb.append("</br>");
            }
        } else {
            sb.append("없음");
        }
        return sb.toString();
    }
    
}

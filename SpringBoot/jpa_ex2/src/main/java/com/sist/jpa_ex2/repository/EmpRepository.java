package com.sist.jpa_ex2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.jpa_ex2.store.EmpJPO;
import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<EmpJPO, String>{
    //상속 받은 findAll()..., findById() 등이 있는 상태
    List<EmpJPO> findByEname(String ename);

    List<EmpJPO> findByEnameContaining(String ename);

    List<EmpJPO> findByEnameLike(String ename);

    //Contains, Containing, IsContaining 이 세 개의 키워드 간의 차이는 없으므로 가장 짧은 Contains 사용
    List<EmpJPO> findByJobContains(String job);

    List<EmpJPO> findByJobContainsAndDeptno(String job, String deptno);

    List<EmpJPO> findByJobContainsOrDeptno(String job, String deptno);

    List<EmpJPO> findBySalLessThanEqual(int sal); //GreaterThan...
}

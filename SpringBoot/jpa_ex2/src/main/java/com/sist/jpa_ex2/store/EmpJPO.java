package com.sist.jpa_ex2.store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "emp")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmpJPO {
    @Id
    @GeneratedValue
    @Column(name = "empno")
    private String empno;

    @Column(name = "ename")
    private String ename;

    @Column(name = "job")
    private String job;

    @Column(name = "deptno")
    private String deptno;

    @Column(name = "sal")
    private int sal;
}

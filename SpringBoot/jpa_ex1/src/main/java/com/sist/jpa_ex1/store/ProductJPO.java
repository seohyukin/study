package com.sist.jpa_ex1.store;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "product_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductJPO {
    @Id
    @GeneratedValue
    private long pNum;
    private String pName;
    private String pCompany;
    private LocalDate regDate;

    @Column(name = "category1")
    private int category1;

    private int category2;
    private int category3;

    //Category1을 기준으로 하면 일대다(카테고리 하나에 상품 여러개)
    //Product를 기준으로 하면 ManyToOne(상품 여러개에 카테고리 하나)
    @ManyToOne
    @JoinColumn(name = "category1", insertable = false, updatable = false)
    private Category1JPO cvo1; //어디를 참조하는가
}

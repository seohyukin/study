package com.sist.jpa_ex1.store;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "category1_t")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category1JPO {
    @Id
    @GeneratedValue
    private long cIdx;
    private String cName;
    private String desc;
    private int status;
}

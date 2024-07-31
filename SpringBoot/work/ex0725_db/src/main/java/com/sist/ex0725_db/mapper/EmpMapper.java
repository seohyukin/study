package com.sist.ex0725_db.mapper;

import java.util.List;
import java.util.Map;

import com.sist.ex0725_db.vo.EmpVO;

public interface EmpMapper {
    //추상메서드만 정의한다. 이 때 emp.xml의 id들과 동일한 이름의 추상메서드를 정의해야 한다. (emp.xml의 namespace를 현재 인터페이스로 지정했기 때문에)
    //(emp.xml에 namespace를 찾아가서 sql문을 하나의 객체로 보고 그 sql문이 구동가능한지 알아서 검사)

    //emp.xml에 resultType이 EmpVO이고 여러 개일 경우 배열이 아닌 리스트로 받으므로 리스트로 받기 (이 이유로 전에 toarray 사용한 것)
    List<EmpVO> all(); //어차피 인터페이스 안에 있는 List<EmpVO>를 재정의 할 때 무조건 반환형이 public으로 재정의되므로 public을 생략한다.

    List<EmpVO> list(String begin, String end);

    List<EmpVO> search(String searchType, String searchValue);
}

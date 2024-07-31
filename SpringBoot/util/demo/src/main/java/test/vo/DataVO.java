package test.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor //생성자가 이미 있으므로 기본 생성자 생성 불가
@NoArgsConstructor //기본 생성자
public class DataVO {
    private String msg;    
}

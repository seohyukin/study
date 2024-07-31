package com.sist.app.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BbsVO {
    private String b_idx, subject, writer, content, file_name, ori_name, write_date, ip, hit, bname, status;
    private List<CommVO> c_list; //댓글들
    
    //Spring환경에서는 file_name을 가져올 때 MultipartFile이라는 객체에 담아야 한다.
    private MultipartFile file; //첨부파일

}

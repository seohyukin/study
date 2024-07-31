package com.sist.ex0726_secure.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sist.ex0726_secure.mapper.MemberMapper;
import com.sist.ex0726_secure.vo.MemVO;

@Service
public class MemberService {
    
    @Autowired
    private MemberMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int reg(MemVO mvo) {
        //reg.jsp에서 전달되는 m_id, m_pw, m_name이 컨트롤러에서 mvo로 받은 것을 그대로 인자로 받았다.

        //비밀번호만 암호화 시키자
        //String pw = passwordEncoder.encode(mvo.getM_pw());
        //mvo.setM_pw(pw);
        mvo.setM_pw(passwordEncoder.encode(mvo.getM_pw()));

        int cnt = mapper.reg(mvo);

        return cnt;
    }

    public MemVO login(MemVO vo) {
        //DB로부터 vo에 있는 m_id를 보내어 해당 MemVO를 받아서 반환한다.
        //이 때 비밀번호가 일치하는지는 passwordEncoder에게 물어봐야 한다.
        MemVO mvo = mapper.login(vo.getM_id());

        //사용자가 입력한 아이디가 잘못되었다면 mvo에는 null이 저장된다.
        if (mvo != null) {
            //아이디는 일치하므로 비밀번호만 비교
            if (passwordEncoder.matches(vo.getM_pw(), mvo.getM_pw())) { //(사용자가 입력한 값, DB에 있는 값)
                return mvo;
            }
        }
        return null;
    }
}

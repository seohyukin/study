package mybatis.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.MemVO;

@Component
public class MemDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public MemDAO() {
		System.out.println("MemDAO 생성");
	}
	
	public MemVO login(String id, String pw) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("mid", id);
		map.put("mpw", pw);
		
		return sqlSession.selectOne("mem.login", map);
	}
}

package mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import mybatis.vo.EmpVO;

@Controller
public class EmpDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public EmpVO emp(String empno) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("num", empno);
		
		return sqlSession.selectOne("emp.search", map);
	}
	
	public List<EmpVO> empAll() {

		return sqlSession.selectList("emp.all");
	}
}

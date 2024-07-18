package mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.EmpVO;

@Component
public class EmpDAO {
	
	@Autowired
	private SqlSessionTemplate ss;
	
	//사원들을 배열로 반환하는 기능
	public EmpVO[] getList() {
		EmpVO[] e_ar = null;
		
		List<EmpVO> e_list = ss.selectList("emp.all");
		
		//list가 null이 아니고 비어있지 않다면 배열로 변환
		if (e_list != null && !e_list.isEmpty()) {
			e_ar = new EmpVO[e_list.size()];
			//list의 요소를 비어있는 배열에 복사
			e_list.toArray(e_ar);
		}
		return e_ar;
	}
	
	public EmpVO[] search(String searchType, String searchValue) {
		EmpVO[] ar = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);
		
		List<EmpVO> e_list = ss.selectList("emp.search", map);
		
		//list가 null이 아니고 비어있지 않다면 배열로 변환
		if (e_list != null && !e_list.isEmpty()) {
			ar = new EmpVO[e_list.size()];
			//list의 요소를 비어있는 배열에 복사
			e_list.toArray(ar);
		}
		return ar;
	}
}

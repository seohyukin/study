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

	public EmpVO[] total() {
		EmpVO[] e_ar = null;

		List<EmpVO> e_list = ss.selectList("emp.total");

		if (e_list != null && !e_list.isEmpty()) {
			e_ar = new EmpVO[e_list.size()];

			e_list.toArray(e_ar);
		}
		return e_ar;
	}

	//비동기식O(emp_list)
//	public EmpVO[] search(String searchType, String searchValue) { 
//		EmpVO[] e_ar = null;
//		  
//		Map<String, String> map = new HashMap<String, String>();
//		  
//		map.put("searchType", searchType); 
//		map.put("searchValue", searchValue);
//	  
//		List<EmpVO> e_list = ss.selectList("emp.search", map);
//	  
//		if (e_list != null && e_list.size() > 0) { 
//			e_ar = new EmpVO[e_list.size()];
//	 
//			e_list.toArray(e_ar); 
//		} 
//		return e_ar; 
//	}
	  
	//비동기식X(emp_list2)
	public EmpVO[] search(Map<String, String> map) {
		EmpVO[] e_ar = null;

		List<EmpVO> e_list = ss.selectList("emp.search", map);

		if (e_list != null && e_list.size() > 0) {
			e_ar = new EmpVO[e_list.size()];

			e_list.toArray(e_ar);
		}
		return e_ar;
	}

}

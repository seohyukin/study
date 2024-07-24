package mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.DeptVO;

@Component
public class DeptDAO {

	@Autowired
	private SqlSessionTemplate ss;
	
	public DeptVO[] getList() {
		DeptVO[] ar = null;
		
		List<DeptVO> list = ss.selectList("dept.all");
		
		if (list != null && !list.isEmpty()) {
			ar = new DeptVO[list.size()];
			
			list.toArray(ar);
		}
		return ar;
	}
	
	public DeptVO[] search(String searchType, String searchValue) {
		DeptVO[] ar = null;
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);
		
		List<DeptVO> list = ss.selectList("dept.search", map);
		
		if (list != null && list.size() > 0) {
			ar = new DeptVO[list.size()];
			
			list.toArray(ar);
		}
		return ar;
	}
}

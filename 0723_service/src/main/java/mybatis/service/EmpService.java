package mybatis.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mybatis.dao.EmpDAO;
import mybatis.vo.EmpVO;

@Service
public class EmpService implements MyMapper {

	@Autowired
	private EmpDAO e_dao;

	@Override
	public EmpVO[] getList() {
		// TODO Auto-generated method stub
		return e_dao.total();
	}

	//비동기식O(emp_list)
//	@Override 
//	public EmpVO[] search(String searchType, String searchValue) {
//		return e_dao.search(searchType, searchValue);
//	}

	//비동기식X(emp_list2)
	@Override
	public EmpVO[] search(String searchType, String searchValue) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);

		return e_dao.search(map);
	}

}

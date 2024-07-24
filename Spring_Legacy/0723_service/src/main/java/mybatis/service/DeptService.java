package mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mybatis.dao.DeptDAO;
import mybatis.vo.DeptVO;

@Service
public class DeptService implements MyMapper {

	@Autowired
	private DeptDAO d_dao;
	
	@Override
	public DeptVO[] getList() {
		
		return d_dao.total();
	}

	@Override
	public DeptVO[] search(String searchType, String searchValue) {
		return d_dao.search(searchType, searchValue);
	}

}

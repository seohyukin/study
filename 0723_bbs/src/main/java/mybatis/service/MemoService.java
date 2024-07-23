package mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mybatis.dao.MemoDAO;
import mybatis.vo.MemoVO;

@Service
public class MemoService implements MyMapper {

	@Autowired
	private MemoDAO m_dao;
	
	@Override
	public MemoVO[] getList() {
		return m_dao.total();
	}

}

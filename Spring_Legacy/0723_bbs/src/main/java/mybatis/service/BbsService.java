package mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

@Service
public class BbsService implements MyMapper {

	@Autowired
	private BbsDAO b_dao;
	
	@Override
	public BbsVO[] getList() {
		return b_dao.total();
	}

}

package mybatis.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.BbsVO;

@Component
public class BbsDAO {

	@Autowired
	private SqlSessionTemplate ss;
	
	public BbsVO[] total() {
		BbsVO[] b_ar = null;
		
		List<BbsVO> b_list = ss.selectList("bbs.total");
		
		if (b_list != null && !b_list.isEmpty()) {
			b_ar = new BbsVO[b_list.size()];
			b_list.toArray(b_ar);
		}
		return b_ar;
	}
	
}

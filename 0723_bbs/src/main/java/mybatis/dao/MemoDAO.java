package mybatis.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.MemoVO;

@Component
public class MemoDAO {

	@Autowired
	private SqlSessionTemplate ss;
	
	public MemoVO[] total() {
		MemoVO[] m_ar = null;
		
		List<MemoVO> m_list = ss.selectList("memo.total");
		
		if (m_list != null && !m_list.isEmpty()) {
			m_ar = new MemoVO[m_list.size()];
			m_list.toArray(m_ar);
		}
		return m_ar;
	}
}

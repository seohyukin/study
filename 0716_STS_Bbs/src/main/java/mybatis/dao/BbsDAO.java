package mybatis.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.BbsVO;
import mybatis.vo.CommVO;

@Component
public class BbsDAO {
	
	@Autowired
	private SqlSessionTemplate ss;
	
	public int getCount(String bname) {
		//인자의 이름은 상관 X (bname이 아니어도 됨)
		int cnt = ss.selectOne("bbs.count", bname);
		
		return cnt;
	}
	
	public BbsVO[] getList(String bname, int begin, int end) {
		BbsVO[] ar = null;
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("bname", bname);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		List<BbsVO> list = ss.selectList("bbs.list", map);
		if (list != null && list.size() > 0) {
			ar = new BbsVO[list.size()];
			list.toArray(ar); //list가 가지는 모든 요소들은 ar이라는 배열에 복사
		}
		
		return ar;
	}

	//원글을 저장하는 기능
	public int add(BbsVO vo) {
		
		return ss.insert("bbs.add", vo);
	}
	
	//b_idx값을 인자로 받아서 게시물을 검색하여 보기 기능을 수행하는 메서드
	public BbsVO getBbs(String b_idx) {
		
		return ss.selectOne("bbs.getBbs", b_idx);
	}
	
	//보기 기능에서 조회수 증가하는 메서드
	public int hit(String b_idx) {
		
		return ss.update("bbs.hit", b_idx);	
	}
	
	//원글 수정 메서드
	public int edit(BbsVO vo) {

		return ss.update("bbs.edit", vo);
	}
	
	//원글 삭제 메서드
	public int del(String b_idx) {
		int cnt = ss.update("bbs.del", b_idx);
		if (cnt > 0) {
			ss.commit();
		} else {
			ss.rollback();
		}
		
		return cnt;
	}
	
	//댓글 저장 메서드
	public int addComm(CommVO cvo) {

		return ss.insert("comm.addComm", cvo);
	}

}

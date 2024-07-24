package bbs.util;

public class Paging {
	int pagePerBlock = 5; //블럭
	int startPage; //한 블럭의 시작 페이지 값
	int endPage; //한 블럭의 끝 페이지 값
	int nowPage = 1; //현재 페이지
	int totalPage; //총 페이지 수
	int totalRecord; //총 게시물 수
	int numPerPage = 10; //한 페이지 당 게시물 수
	int begin; //현재 페이지 값에 따라 bbs_t테이블에서 가져올 게시물의 시작 행번호
	int end; //현재 페이지 값에 따라 bbs_t테이블에서 가져올 게시물의 마지막 행번호
	
	public Paging() {
		//기본생성자 (useBean같은 거를 쓸 수도 있기 때문에 만들어 놓기)
	}
	
	public Paging(int numPerPage, int pagePerBlock) {
		//인자인 지역변수를 멤버변수에 저장
		this.numPerPage = numPerPage;
		this.pagePerBlock = pagePerBlock;
	}

	public int getPagePerBlock() {
		return pagePerBlock;
	}

	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		//현재 페이지 값이 바뀌는 순간 아래 모든 변수들을 구해준다.
		this.nowPage = nowPage;
		//현재 페이지 값이 변경되고 있으니 begin과 end 그리고 startPage와 endPage값을 구한다.
		//먼저 무슨 페이지든 간에 총 페이지를 넘으면 안된다.
		if (nowPage > totalPage) {
			nowPage = totalPage;
		}
		
		//각 페이지의 시작 레코드의 행번호와 마지막 레코드의 행번호 지정
		//현재 페이지 값이 1 -> begin: 1, end: 10
		//현재 페이지 값이 2 -> begin: 11, end: 20
		begin = (nowPage - 1) * numPerPage + 1;
		end = nowPage * numPerPage;
		
		//현재 페이지 값으로 블럭의 시작페이지 값 구하기
		startPage = (int)((nowPage - 1) / pagePerBlock) * pagePerBlock + 1;
		endPage = startPage + pagePerBlock - 1;
		
		//마지막 페이지 값이 총 페이지 값보다 크면 안된다.
		if (endPage > totalPage) {
			endPage = totalPage;
		}
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		//총 게시물의 수가 변경될 때 총 페이지 값도 변경되도록 하자
		this.totalRecord = totalRecord;
		
		/*
		this.totalPage = totalRecord / numPerPage;
		if ((totalRecord % numPerPage) != 0) {
			this.totalPage++;
		}
		*/
		this.totalPage = (int)(Math.ceil((double)totalRecord / numPerPage));
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}

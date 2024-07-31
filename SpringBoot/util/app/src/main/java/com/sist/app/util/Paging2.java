package com.sist.app.util;

public class Paging2 {
	int startPage; //한 블럭의 시작 페이지 값
	int endPage; //한 블럭의 끝 페이지 값
	int totalPage; //총 페이지 수
	int nowPage = 1; //현재 페이지
	int totalRecord; //총 게시물 수
	int numPerPage = 10; //한 페이지 당 게시물 수
	int pagePerBlock = 5; //블럭 1개당 페이지 수
	int begin; //현재 페이지 값에 따라 bbs_t테이블에서 가져올 게시물의 시작 행번호
	int end; //현재 페이지 값에 따라 bbs_t테이블에서 가져올 게시물의 마지막 행번호
	
	private boolean isPrePage; //이전 기능 가능여부(true일 때 이전 기능 활성화)
	private boolean isNextPage; //다음 기능 가능여부(true일 때 다음 기능 활성화)
	
	//JSP에서 표현할 페이징 HTML코드를 생성하여 저장할 곳
	private StringBuffer sb;
	
	public Paging2() {
		//기본생성자
	}
	
	public Paging2(int nowPage, int totalRecord, int numPerPage, int pagePerBlock, String bname) {
		//인자인 지역변수를 멤버변수에 저장
		this.nowPage = nowPage;
		this.totalRecord = totalRecord;
		this.numPerPage = numPerPage;
		this.pagePerBlock = pagePerBlock;
		
		//이전/다음 기능 초기화 (boolean형이여서 원래 false지만 혹시 모르므로 한 번 더 초기화)
		isPrePage = false;
		isNextPage = false;
		
		//총 게시물의 수를 통해 총 페이지 수 구하기
		this.totalPage = (int)(Math.ceil((double)totalRecord / numPerPage));
		
		//현재 페이지 값이 총 페이지 수보다 크다면 현재 페이지 값을 총 페이지 수로 지정
		if (nowPage > totalPage) {
			nowPage = totalPage;
			this.nowPage = nowPage;
		}
		
		//현재 블럭의 시작 페이지 값과 마지막 페이지 값을 구하자
		//nowPage가 pagePerBlock보다 크지 않으면 startPage는 무조건 1이다.
		startPage = (int)((nowPage - 1) / pagePerBlock) * pagePerBlock + 1;
		endPage = startPage + pagePerBlock - 1;
		
		//마지막 페이지 값이 전체 페이지 값보다 크다면 마지막 페이지 값을 전체 페이지 값으로 지정
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		//각 페이지의 시작 레코드의 행번호와 마지막 레코드의 행번호 지정
		//현재 페이지 값이 1 -> begin: 1, end: 10
		//현재 페이지 값이 2 -> begin: 11, end: 20
		begin = (nowPage - 1) * numPerPage + 1;
		end = nowPage * numPerPage;
		//end가 총 게시물의 수보다 크다고 해서 오류는 발생하지 않음 (SQL문에서 범위가 벗어난 부분은 알아서 무시함)
		
		//이전 기능 가능 여부 확인
		if (startPage > 1) {
			isPrePage = true;
		}
		
		//다음 기능 가능 여부 확인
		if (endPage < totalPage) {
			isNextPage = true;
		}
		
		//현재 페이지 값도 알고, 시작 페이지와 마지막 페이지 값을 알고 있으므로 페이징 기법에 사용할 HTML코드를 작성하여 StringBuffer에 저장해야 한다.
		sb = new StringBuffer("<ol class='paging'>");
		
		//이전 기능 여부
		if (isPrePage) {
			sb.append("<li><a href='list?bname=");
			sb.append(bname);
			sb.append("&cPage=");
			sb.append(nowPage - pagePerBlock);
			sb.append("'>&lt;</a></li>");
		} else {
			sb.append("<li class='disable'>&lt;</li>");
		}
		
		//페이지 번호를 출력하는 반복문
		for (int i = startPage; i <= endPage; i++) {
			//i가 현재 페이지 값과 같을 때는 a태그를 지정하지 않고 숫자만 출력
			if (i == nowPage) {
				sb.append("<li class='now'>");
				sb.append(i);
				sb.append("</li>");
			} else {
				sb.append("<li><a href='list?bname=");
				sb.append(bname);
				sb.append("&cPage=");
				sb.append(i); //전달되는 파라미터
				sb.append("'>");
				sb.append(i); //화면에 표현되는 페이지 번호
				sb.append("</a></li>");
			}
		}
		
		//다음 기능 여부
		if (isNextPage) {
			sb.append("<li><a href='list?bname=");
			sb.append(bname);
			sb.append("&cPage=");
			sb.append(nowPage + pagePerBlock);
			sb.append("'>&gt;</a></li>");
		} else {
			sb.append("<li class='disable'>&gt;</li>");
		}
		
		sb.append("</ol>");
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
	
	public StringBuffer getSb() {
		return sb;
	}
}

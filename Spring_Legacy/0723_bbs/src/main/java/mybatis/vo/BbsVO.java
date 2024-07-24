package mybatis.vo;

import org.springframework.web.multipart.MultipartFile;

public class BbsVO {
	private String 	b_idx, subject, writer, content, file_name, ori_name, pwd, write_date, ip, hit, bname, status, rnum;
					//b_idx로는 bbs와 notice의 번호가 구분되지 않으므로 rnum을 생성해서 Getter/Setter 만들기
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getB_idx() {
		return b_idx;
	}

	public void setB_idx(String b_idx) {
		this.b_idx = b_idx;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getOri_name() {
		return ori_name;
	}

	public void setOri_name(String ori_name) {
		this.ori_name = ori_name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHit() {
		return hit;
	}

	public void setHit(String hit) {
		this.hit = hit;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRnum() {
		return rnum;
	}

	public void setRnum(String rnum) {
		int r_num = (int)Double.parseDouble(rnum); 
		//bbs.xml에서 rnum이 double형으로 넘어와서 list.jsp에서 vo.getRnum()이 double형으로 찍힌다. 
		//String형인 rnum을 double형으로 변환 후 다시 int형으로 변환하기
		this.rnum = String.valueOf(r_num); //int형의 r_num을 String형으로 변환하고 멤버변수 rnum에 넣기
	}
	
	
}

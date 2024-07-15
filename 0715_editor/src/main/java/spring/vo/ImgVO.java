package spring.vo;

import org.springframework.web.multipart.MultipartFile;

public class ImgVO {
	//파라미터와 이름이 같은 멤버변수 선언
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}

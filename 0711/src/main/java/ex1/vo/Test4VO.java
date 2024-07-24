package ex1.vo;

public class Test4VO {
	private Test2VO test; //Test4VO와 Test2VO의 의존성 생김 (Test4VO는 반드시 Test2VO를 가지고 있어야 함)

	public Test2VO getTest() {
		return test;
	}

	public void setTest(Test2VO test) {
		this.test = test;
	}
	
	
}

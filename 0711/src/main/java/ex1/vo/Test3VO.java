package ex1.vo;

public class Test3VO {
	private String name;
	private int age;
	private boolean live;
	
	//Test3VO객체가 생성될 때 name, age, live라는 객체가 생성되어야 한다. -> 생성자를 통해서 값을 넣어주는 것 -> setter 필요 X but 그냥 getter, setter 둘 다 만들기
	public Test3VO(String name, int age, boolean live) {
		super();
		this.name = name;
		this.age = age;
		this.live = live;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	

}

package Test;

import java.util.List;

public class helloimpl1 implements Hello {
	
	private String name;
	private Hello hello1;
	private List list;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hello getHello1() {
		return hello1;
	}

	public void setHello1(Hello hello1) {
		this.hello1 = hello1;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	@Override
	public void sayHello(String a) {
		System.out.println(a+"..........Œ“ «hello1");
		
	}

}

package Test;

import com.gxh.mapper.BookMapper;

public class test2 {

	public static void main(String[] args) {
		
			System.out.println(BookMapper.class);
			try {
				Class<?> forName = Class.forName("com.gxh.mapper.BookMapper");
				System.out.println(forName);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}

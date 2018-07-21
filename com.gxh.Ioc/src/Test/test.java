package Test;

import java.util.List;

import com.Ioc.Xml.ClassPathXmlFactory;
import com.Ioc.core.BeanFactory;
import com.gxh.service.Test;
import com.gxh.service.UserService;
import com.Ioc.PackageScanner.Scanner;

public class test {

	
	public static void main(String[] args) {
		ClassPathXmlFactory classPathXmlFactory=new ClassPathXmlFactory("config/application.xml");
		BeanFactory beanFactory = classPathXmlFactory.getBeanFactory();
		Scanner scanner = (Scanner) beanFactory.get("mapperScaner");
		scanner.ContantDi();
		UserService service = (UserService) beanFactory.get("user");
		service.add();
}
	}

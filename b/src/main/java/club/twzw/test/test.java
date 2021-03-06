package club.twzw.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import club.twzw.service.UserService;

public class test {
	@Test
	public void testQueryUserList() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		UserService service = (UserService) ac.getBean("UserService"); // 因为给service起了别名，所以通过id的方式获取class
		service.findAll();
	}
}

package b;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import club.twzw.service.UserService;

public class a {
	@Test
	public void testQueryUserList() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		UserService service = (UserService) ac.getBean("UserService"); // ��Ϊ��service���˱���������ͨ��id�ķ�ʽ��ȡclass
		service.findAll();

	}
}
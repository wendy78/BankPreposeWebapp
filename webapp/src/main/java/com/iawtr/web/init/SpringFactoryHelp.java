package com.iawtr.web.init;
import org.springframework.context.ApplicationContext;

public class SpringFactoryHelp {
	private static ApplicationContext ac = null;
	
	public static ApplicationContext getApplicationContext(){
//		if (ac == null){
//			ac = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
//		}
		return ac;
	}
	public static void setApplicationContext(ApplicationContext applicationContext){
		if (ac == null){
			SpringFactoryHelp.ac=applicationContext;
			System.out.println("ac:"+ac);
		}
	}
	public static void main(String[] args){
		SpringFactoryHelp.getApplicationContext();
	}
}

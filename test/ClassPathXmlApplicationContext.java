package test;

import java.rmi.RemoteException;

public class ClassPathXmlApplicationContext implements ApplicationContext{
	private String xmlpath;
	protected ClassPathXmlApplicationContext(String[]path)  {
		this.xmlpath=path[0];
        
	}
}

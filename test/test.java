package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

import factory.BeanFactory;
import factory.XMLBeanFactory;
import resource.LocalFileResource;




public class test {

    public static void main(String[] args) throws ClassNotFoundException, IOException, NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	parseAnnotation();
LocalFileResource resource = new LocalFileResource("beans.xml");
BeanFactory beanFactory = new XMLBeanFactory(resource);
boss b =(boss)beanFactory.getBean("Boss");
System.out.println(b.tostring());
    }
    
    public static void parseAnnotation() throws ClassNotFoundException, IOException{
    	String m_string;
    	byte[] tempbytes = new byte[4096];
    	InputStream in = null;
    	int byteread = 0;
		in = new FileInputStream("bean.xml");
		int count=0;
		while ((byteread = in.read(tempbytes)) != -1) {
			System.out.write(tempbytes, 0, byteread);
			
		}
		System.out.println();
		m_string = new String(tempbytes, "gbk");
	
		System.out.println(count);
       int a=m_string.indexOf("</bean>");
       
        Class clazz = car.class;
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {       	
            Component testA = ( Component)annotation;   
            if(m_string.indexOf("bean id=\""+testA.value())==-1){
        	String m_string1;   
        	OutputStreamWriter pw = null;
    	pw = new OutputStreamWriter(new FileOutputStream("bean.xml"));	
    	   pw.write(m_string.substring(0,a+7));
    	   pw.write("\r\n");
    		 m_string1 = "    <bean id=\""+testA.value()+"\" class=\"test."+testA.value()+"\"></bean>";
    		 pw.write(m_string1);
    		  pw.write(m_string.substring(a+7,m_string.lastIndexOf(">")));    		
    		pw.close();
    	}
        	   }
        }
    
  
    
}
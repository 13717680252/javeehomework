package servletcontainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletProcessor1 {
	  
	  public void process(MyRequest request, MyResponse res) {

	        String uri = request.getParameter("Uri");
	        
	        String servleturi = uri.substring(uri.lastIndexOf("/") );
	        	        
	        String servletName=this.getclassname(servleturi);	        
	        
	        if (servletName .equals("404")) {
	            String msg = "HTTP/1.1 404 Not Found\r\nContent-Type: text/plain\r\n\r\n404 Not Found";
	            try { PrintWriter out = res.getWriter();	            	     	           
	                out.print(msg);
	                out.close(); 
	                return;
	            }catch (IOException e){
	                e.printStackTrace();
	            }}
	       
	            else{

	            	 try {     
	            		  File dir=new File("src");
	                      URL url=dir.toURL();
	            		  //URL url = new URL("src"); 	            		 
	                     URL[] urls=new URL[]{url};
	                     ClassLoader loader=new URLClassLoader(urls);
	                     Class myClass = loader.loadClass(servletName);
	                     Servlet servlet = (Servlet) myClass.newInstance();
	                     servlet.init(null);
	                     servlet.service(request, res);
	                     servlet.destroy();
	                 } catch (ClassNotFoundException e) {
	                     e.printStackTrace();
	                 } catch (InstantiationException e) {
	                     e.printStackTrace();
	                 } catch (IllegalAccessException e) {
	                     e.printStackTrace();
	                 } catch (IOException e) {
	                     e.printStackTrace();
	                 }catch (ServletException e){
	                     e.printStackTrace();
	                 }
	             }
	           return; 	           	
	            	
	    }
	        
	 	        
public String getclassname(String uri){
	String fileName="web.xml";
	String[][]list=new String[10][];
	for (int i=0;i<10;i++){
		list[i]=new String[2];
	}
	  File file = new File(fileName);
	  InputStream in = null; 
	  byte[] tempbytes = new byte[2048];
	  try {  
      int byteread = 0;
      in = new FileInputStream(fileName);
     
      
      while ((byteread = in.read(tempbytes)) != -1) {
          System.out.write(tempbytes, 0, byteread);
      }
  } catch (Exception e1) {
      e1.printStackTrace();
  } finally {
      if (in != null) {
          try {
              in.close();
          } catch (IOException e1) {
          }
      }
	  
  }  
	  String res = new String(tempbytes);
	  int a,b=0;
	  int c=0;
	  
     a=res.indexOf("<servlet-class>",b);  
    while(a!=-1){
     a+=15;
     b=res.indexOf("</servlet-class>",a);
     list[c][0]=res.substring(a,b);
     a=res.indexOf("<url-pattern>",b)+13;    
     b=res.indexOf("</url-pattern>",a);
     list[c][1]=res.substring(a, b);     
     a=res.indexOf("<servlet-class>",b);
     c++;
     }
    for(int i=0;i<c;i++){
    	if(uri.equals(list[i][1])){
    		return list[i][0];
    	}
    }
	return "404";
 
}
	  	  
	  
	}

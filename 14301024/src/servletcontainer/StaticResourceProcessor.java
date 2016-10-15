package servletcontainer;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;



public class StaticResourceProcessor {
	 private static String msg;

	  public void process(MyRequest req, MyResponse res) {
		  try{
	            String fileName=req.getParameter("Uri").substring(1);//"/index.html"->"index.html"
	            String content=new String(Files.readAllBytes(Paths.get("res/"+fileName)));
	            String head="HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
	            msg=head+content;
	        }catch (Exception e){
	            e.printStackTrace();
	            msg="HTTP/1.1 404 Not Found\r\nContent-Type: text/plain\r\n\r\n404 Not Found";
	        }
	        try{
	            PrintWriter writer=res.getWriter();
	            writer.print(msg);
	            writer.flush();
	            writer.close();
	        }catch (IOException e){
	            e.printStackTrace();
	        }
	    }
	}

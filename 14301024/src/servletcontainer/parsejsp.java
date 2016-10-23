package servletcontainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class parsejsp {
	byte[] tempbytes = new byte[4096];
	String m_string;
	private String m_url;
	int[][] position;
	String[] list;
	int count;

	public parsejsp(String url) throws IOException {
		this.m_url = url.substring(0, url.length() - 4);
		list = new String[20];
		count = 0;
		position = new int[20][];
		for (int i = 0; i < 20; i++) {
			position[i] = new int[2];
		}		
		this.writexml();				
		this.m_url += "Servlet.java";
		InputStream in = null;
		try {
			int byteread = 0;
			in = new FileInputStream(url);
			while ((byteread = in.read(tempbytes)) != -1) {
				System.out.write(tempbytes, 0, byteread);
			}
			System.out.println();
			m_string = new String(tempbytes, "gbk");

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
	}



	public void separate() {
		String current = m_string;
		int a = 0;
		int b = 0;
		while (true) {
			a = m_string.indexOf("<%", b);
			if (a < 0) {
				break;
			}
			b = m_string.indexOf("%>", a);
			if (b != -1) {
				list[count] = m_string.substring(a + 2, b);
				System.out.println(list[count]);
				position[count][0] = a;
				position[count][1] = b + 2;
				count++;
			}
		}
		return;
	}

	 public void writecommonatveryfirst(OutputStreamWriter pw) throws IOException {
	        pw.write("package Servlet;\n");
	    }

	//waiting to be completed
    public void writecommonbeforeoutput(OutputStreamWriter pw) throws IOException {
        pw.write("import javax.servlet.*;import java.io.IOException;import java.io.PrintWriter;");
        pw.write("public class " +m_url+"Servlet"+" implements Servlet{\n");
        pw.write("public void init(ServletConfig config) throws ServletException {\n" +
                "        System.out.println(\"init\");\n" +
                "    }\n" +
                "\n" +
                "    public void service(ServletRequest request, ServletResponse response)\n" +
                "            throws ServletException, IOException {\n" +
                "        System.out.println(\"from service\");" +
                "PrintWriter out = response.getWriter();" +
                "out.write(\"HTTP/1.1 200 OK\");" +
                "out.write(\"\\n\");" +
                "out.write(\"Content-Type: text/html\");" +
                "out.write(\"\\n\");" +
                "out.write(\"\\n\");");
    }

    public void writecommonafteroutput(OutputStreamWriter pw) throws IOException {
        pw.write("out.flush();" +
                "out.close();" +
                "}\n" +
                "\n" +
                "    public void destroy() {\n" +
                "        System.out.println(\"destroy\");\n" +
                "    }\n" +
                "\n" +
                "    public String getServletInfo() {\n" +
                "        return null;\n" +
                "    }\n" +
                "\n" +
                "    public ServletConfig getServletConfig() {\n" +
                "        return null;\n" +
                "    }\n" +
                "}");
    }

	public void writelable(String s, OutputStreamWriter pw) throws IOException {
		int a = 0;
		int b = 0;
		s = s.replaceAll("\n", "");
		s = s.replaceAll("\r", "");
		s.trim();
		while (true) {
			a = s.indexOf("<", b);
			if (a < 0) {
				break;
			}
			if (a > b + 1) {
				pw.write("out.write(\"");
				String c = s.substring(b + 1, a);
				pw.write(c);
				pw.write("\");\r\n");
				b = a;
				continue;
			}
			b = s.indexOf(">", a);
			if (b != -1) {
				pw.write("out.write(\"");
				String c = s.substring(a, b + 1);
				pw.write(c);
				pw.write("\");\r\n");

			}
		}
	}

	public void headparsing(OutputStreamWriter pw) throws IOException{
	for(int i=0;i<count;i++){
		if (list[i].substring(0, 1).equals("@")){
			int a=list[i].indexOf("import");
			if(a>0){
			String im=list[i].substring(a+8,list[i].indexOf("\"", a+8));
			int b=im.indexOf(",");
			while(b>0){
				pw.write("import ");
				pw.write(im.substring(b));
				pw.write(";\r\n");
				im=im.substring(b+1, im.length());
				b=im.indexOf(",");
			}
			pw.write("import ");
			pw.write(im);
			pw.write(";\r\n");
		}
	}
	}
}
	
	//first to be executed
	private static String fileName="web.txt";
	public void writexml() throws IOException{

		  File file = new File(fileName);
		  InputStream in = null; 
		  byte[] mbytes = new byte[4096];
		  try {  
	      int byteread = 0;
	      in = new FileInputStream(fileName);
	     	      
	      while ((byteread = in.read(mbytes)) != -1) {
	          System.out.write(mbytes, 0, byteread);
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
		  
	String xml=new String(mbytes);
	if(xml.indexOf(m_url+".jsp")<0){
	int pos=xml.indexOf("<servlet>");
	OutputStreamWriter pw = null;
	pw = new OutputStreamWriter(new FileOutputStream("web.xml"));
	pw.write(xml.substring(0,pos));
	pw.write("\r\n");
	pw.write("<servlet>\r\n");
	String servletname=m_url+"Servlet";
	pw.write("  <servlet-name>"+servletname+"</servlet-name>\r\n");
	pw.write("  <servlet-class>Servlet."+servletname+"</servlet-class>\r\n");
	pw.write("</servlet>\r\n");
	pw.write("<servlet-mapping>\r\n");
	pw.write("  <servlet-name>"+servletname+"</servlet-name>\r\n");
	pw.write("  <url-pattern>Servlet."+m_url+".jsp</url-pattern>\r\n");	
	pw.write("</servlet-mapping>\r\n");
	pw.write(xml.substring(pos,xml.length()));
	pw.close();
	}
	}
	
	
	
	
	public void generateservlet() throws IOException {
		OutputStream out = null;
		try {
			OutputStreamWriter pw = null;
			pw = new OutputStreamWriter(new FileOutputStream("src/Servlet/" + m_url ));
			   writecommonatveryfirst(pw);
			   
			this.separate();
            headparsing(pw);
			//waiting for insert necessity
            // 内容
            writecommonbeforeoutput(pw);
			for (int i = 0; i < count; i++) {
				if (i > 0) {
					if (!list[i - 1].substring(0, 1).equals("@")) {
						if (!list[i - 1].substring(0, 1).equals("="))
							pw.write(list[i - 1]);
						else
							pw.write("out.write("
									+ (list[i - 1].substring(1,
											list[i - 1].length())) + ");\r\n");
					}
					pw.write("\r\n");
					String s = (m_string.substring(position[i - 1][1],
							position[i][0]));
					s.trim();
					writelable(s, pw);

				} else {
					String s = (m_string.substring(0, position[i][0]));
					s.trim();
					writelable(s, pw);
				}
			}
			if (!list[count - 1].substring(0, 1).equals("@")) {
				if (!list[count - 1].substring(0, 1).equals("=")) {
					pw.write(list[count - 1]);
				} else {
					pw.write("out.write("
							+ (list[count - 1].substring(1,
									list[count - 1].length())) + ");\r\n");
				}
			}
			pw.write("\r\n");
			String s = (m_string.substring(position[count - 1][1],
					m_string.length()));
			s.trim();
			writelable(s, pw);
		 writecommonafteroutput(pw);
			pw.close();
		      //JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();  
		        //int results = compiler.run(null, null, null, "src/Servlet/" + m_url ); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}

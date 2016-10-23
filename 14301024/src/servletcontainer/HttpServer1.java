package servletcontainer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class HttpServer1 {
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	// the shutdown command received
	private boolean shutdown = false;

	public static void main(String[] args) throws IOException {
		HttpServer1 server = new HttpServer1();
			server.await();
	}

	public void await() throws IOException {
		   ServerSocket serverSocket = null;
	        int port =8888;
	        try {
	            //服务器套接字对象
	            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.exit(1);
	        }
	        boolean status=true;
		while (status) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				// create Request object and parse
				MyRequest request = new MyRequest(input);
			 
				// create Response object
				MyResponse response = new MyResponse(output);
						
				if (request.getParameter("Uri")!=null){
					if(request.getParameter("Uri").contains(".html")) {
					StaticResourceProcessor processor = new StaticResourceProcessor();
					processor.process(request, response);
				} else { 
					//run check at first
					parsejsp rd=new parsejsp(request.getParameter("Uri").substring(1));
					rd. generateservlet();
					ServletProcessor1 processor=new ServletProcessor1();				           
                    processor.process(request,response);
					
				}
				}
				socket.close();
				input.close();
	            output.close();
				
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
				status=false;
			}
		}
		serverSocket.close();
		
	}

}

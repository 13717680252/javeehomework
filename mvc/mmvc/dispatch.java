package mmvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class dispatch extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().append("Served at: ").append(request.getContextPath());
  
    ModelAndView mav = new ModelAndView();
    Enumeration<String> pNames = request.getParameterNames();
    HashMap<String,Object> paraMap = new HashMap<>();
    while(pNames.hasMoreElements()){
        String pName = (String) pNames.nextElement();
        paraMap.put(pName,request.getParameter(pName));
    }
    mav.setParamMap(paraMap);

  
    String uri = request.getRequestURI();
    ControllerMap map = ControllerMap.getControllerMap();
    try {
		mav = map.invokeMethod(uri, mav);
	} catch (IllegalAccessException | IllegalArgumentException
			| InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    if(mav == null){
        request.getRequestDispatcher(uri).forward(request, response);
        return;
    }
    Iterator<String> it = mav.getObjectNames().iterator();
    while(it.hasNext()){
        String name = it.next();
        request.setAttribute(name, mav.getObject(name));
    }
    request.getRequestDispatcher(mav.getViewName()).forward(request, response);
}

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   this.doGet(request, response);
}
}
package mmvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import annotations.Controller;
import annotations.RequestMapping;

public class ControllerMap {
	  private static ControllerMap cm = null;
	    private static HashMap<String,Method> uriMethodMap = new HashMap<String,Method>();
	    private static HashMap<String,Object> uriObjectMap = new HashMap<String,Object>();

	    private ControllerMap() {
	        scanpackage scan = new scanpackage("test");
	        try {
	            List<String> list = scan.getFullyQualifiedClassNameList();
	            Iterator<String> it = list.iterator();
	            Class<?> c;
	            while (it.hasNext()) {
	                String className = it.next();
	                c = Class.forName(className);	              
	                if (c.isAnnotationPresent(Controller.class)) {
	                    Method[] methods = c.getDeclaredMethods();
	                    for (int i = 0; i < methods.length; i++) {

	                        if (methods[i].isAnnotationPresent(RequestMapping.class)) {
	                            RequestMapping rm = methods[i].getAnnotation(RequestMapping.class);
	                            String uri = rm.value();
	                            Object obj = uriObjectMap.get(uri);
	                            if (obj == null) {
	                                uriObjectMap.put(uri, c.newInstance());
	                                uriMethodMap.put(uri, methods[i]);
	                            }
	                        }
	                    }

	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e){
	            e.printStackTrace();
	        }
	    }

	    //single instance mode
	    public static ControllerMap getControllerMap(){
	        if(cm == null){
	            cm = new ControllerMap();
	        }
	        return cm;
	    }

	    public ModelAndView invokeMethod(String uri,ModelAndView inMav) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
	        Object obj = uriObjectMap.get(uri);
	        Method method = uriMethodMap.get(uri);
	        if(method == null){
	            return null;
	        }
	        ModelAndView mav = null;
	     
	            mav = (ModelAndView) method.invoke(obj,inMav);
	       
	     
	        return mav;
	    }
}

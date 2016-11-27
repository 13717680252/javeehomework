package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PrintBeforeAdvice implements MethodBeforeAdvice,InvocationHandler{
    //代理对象
	public Object target;
	
	public PrintBeforeAdvice() {
		// TODO Auto-generated constructor stub
	}


	public Object getTarget() {
		return target;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		before(method, args, proxy);
		return method.invoke(getTarget(), args);
	}


	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("Print advice before method...");
		
	}


	public void setTargetObject(Object targetObject) {
		// TODO Auto-generated method stub
		this.target=targetObject;
		
	}

}

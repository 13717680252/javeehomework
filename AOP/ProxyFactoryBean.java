

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactoryBean {

	//代理接口
	public String proxyInterfaces;
	//真实对象
	public Object target;
	//InvocationHandler
	public List<Advice> interceptorNames;
	public String getProxyInterfaces() {
		return proxyInterfaces;
	}
	public void setProxyInterfaces(String proxyInterfaces) {
		this.proxyInterfaces = proxyInterfaces;
	}
	public Object getTarget() {
		return target;
	}
	public void setTarget(Object target) {
		this.target = target;
	}
	public List<Advice> getInterceptorNames() {
		return interceptorNames;
	}
	public void setInterceptorNames(List<Advice> interinterceptorNames) {
		this.interceptorNames = interinterceptorNames;
	}
	
	public Object getProxy(){
	   Object ob = null;
		
	   if(interceptorNames.size()>0){
		   for(int a=0;a<interceptorNames.size();a++){
			   //设置真实对象
			   interceptorNames.get(a).setTargetObject(target);
			   ob=Proxy.newProxyInstance(this.getClass().getClassLoader(), 
					                   target.getClass().getInterfaces(),
					               (InvocationHandler) interceptorNames.get(a));	   
		   }
		   return ob;
	   }else{
		   return target;
	   }

	}
}

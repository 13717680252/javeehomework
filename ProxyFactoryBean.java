package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactoryBean {
	public String proxyInterfaces;
	public Object target;

	public List<Advice> inames;
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
		return inames;
	}
	public void setInterceptorNames(List<Advice> interinterceptorNames) {
		this.inames = interinterceptorNames;
	}
	
	public Object getproxy(){
	   Object ob = null;	
	   if(inames.size()>0){
		   for(int a=0;a<inames.size();a++){
			   inames.get(a).setTargetObject(target);
			   ob=Proxy.newProxyInstance(this.getClass().getClassLoader(), 
					                   target.getClass().getInterfaces(),
					               (InvocationHandler) inames.get(a));	   
		   }
		   return ob;
	   }else{
		   return target;
	   }

	}
}

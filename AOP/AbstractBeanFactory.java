

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



public abstract class AbstractBeanFactory implements BeanFactory{
	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
	
	public Object getBean(String beanName)
	{
		if(beanName.equals("foo")){//代理工厂类则返回代理
			ProxyFactoryBean bean=	(ProxyFactoryBean)this.beanDefinitionMap.get(beanName).getBean();
			return bean.getProxy();
		}
		
		return this.beanDefinitionMap.get(beanName).getBean();
	}
	
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition)
	{
		beanDefinition = GetCreatedBean(beanDefinition);
		this.beanDefinitionMap.put(beanName, beanDefinition);
	}
	
	protected abstract BeanDefinition GetCreatedBean(BeanDefinition beanDefinition);
}

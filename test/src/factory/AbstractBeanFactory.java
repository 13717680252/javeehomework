package factory;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bean.BeanDefinition;

public abstract class AbstractBeanFactory implements BeanFactory{

	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

	

	public Object getBean(String beanName)

	{

		return this.beanDefinitionMap.get(beanName).getBean();

	}

	

	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException

	{

		beanDefinition = GetCreatedBean(beanDefinition);

		this.beanDefinitionMap.put(beanName, beanDefinition);

	}

	

	protected abstract BeanDefinition GetCreatedBean(BeanDefinition beanDefinition) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

}
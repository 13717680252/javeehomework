package factory;

import java.lang.reflect.InvocationTargetException;

import bean.BeanDefinition;

public interface BeanFactory {
	Object getBean(String beanName);
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}

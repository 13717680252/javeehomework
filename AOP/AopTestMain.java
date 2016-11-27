import java.lang.reflect.InvocationTargetException;



public class AopTestMain {
	public static void main(String[] args) {
        LocalFileResource resource = new LocalFileResource("aop.xml");
		BeanFactory beanFactory;
		try {
			beanFactory = new XMLBeanFactory(resource);
		    FooInterface foo = (FooInterface)beanFactory.getBean("foo");
		    foo.printFoo();
		    foo.dummyFoo();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	  }

}

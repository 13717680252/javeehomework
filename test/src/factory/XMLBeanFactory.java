package factory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;























import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import resource.Resource;
import bean.BeanDefinition;
import bean.BeanUtil;
import bean.PropertyValue;
import bean.PropertyValues;
import bean.ReflectionUtils;

public class XMLBeanFactory extends AbstractBeanFactory {

    private String xmlPath;

    public XMLBeanFactory(Resource resource) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document document = dbBuilder.parse(resource.getInputStream());
            NodeList beanList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanList.getLength(); i++) {
                Node bean = beanList.item(i);
                BeanDefinition beandef = new BeanDefinition();
                String beanClassName = bean.getAttributes().getNamedItem("class").getNodeValue();
                String beanName = bean.getAttributes().getNamedItem("id").getNodeValue();

                beandef.setBeanClassName(beanClassName);

                try {
                    //the class has been set, so here we can get beanClass
                    Class<?> beanClass = Class.forName(beanClassName);
                    beandef.setBeanClass(beanClass);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                PropertyValues propertyValues = new PropertyValues();

                NodeList propertyList = bean.getChildNodes();
                for (int j = 0; j < propertyList.getLength(); j++) {
                    Node property = propertyList.item(j);
                    if (property instanceof Element) {
                        Element ele = (Element) property;

                        if (ele.getTagName().equals("property")) {
                            String name = ele.getAttribute("name");
                            Object value = ele.getAttribute("value");
                            //if this dependence injection is set by value
                            if (value.equals("")) {
                                value = ele.getAttribute("ref");
                                Object beRefedBean;
                                beRefedBean = this.getBean((String) value);
                                propertyValues.AddPropertyValue(new PropertyValue(name, beRefedBean, false));
                            } else {
                                Class<?> type = null;
                                try {
                                    type = beandef.getBeanClass().getDeclaredField(name).getType();
                                } catch (NoSuchFieldException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                if (type == Integer.class) {
                                    value = Integer.parseInt((String) value);
                                }

                                propertyValues.AddPropertyValue(new PropertyValue(name, value, false));
                            }
                        }
                        //or if this dependence injection is set by constructor
                        else if (ele.getTagName().equals("constructor-arg")) {
                            String ref = ele.getAttribute("ref");
                            Object beRefedBean;
                            beRefedBean = this.getBean((String) ref);
                            propertyValues.AddPropertyValue(new PropertyValue(ref.toLowerCase(), beRefedBean, true));
                        }
                    }
                }
                beandef.setPropertyValues(propertyValues);

                //use parent's function to instant the bean class in BeanDef and put it into a Map
                this.registerBeanDefinition(beanName, beandef);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BeanDefinition GetCreatedBean(BeanDefinition beanDefinition) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        //Instant a Bean class and add properties
      
            // set BeanClass for BeanDefinition
            Class<?> beanClass = beanDefinition.getBeanClass();
            Object bean = null;
            List<PropertyValue> fieldDefinitionList = beanDefinition.getPropertyValues().GetPropertyValues();
            List<Class<?>> constructorParamTypeList = new ArrayList<Class<?>>();
            for (PropertyValue propertyValue : fieldDefinitionList) {
                if (propertyValue.isConstructorParam()) {
                    
                        Field field = beanClass.getDeclaredField(propertyValue.getName());
                        Class<?> type = field.getType();
                        constructorParamTypeList.add(type);
                 
                }
            }

            if (constructorParamTypeList.isEmpty()) {
                //if all properties are injected by values
                // set Bean Instance for BeanDefinition
                bean = beanClass.newInstance();
            } else {
                //or some property is injected by constructor
                Constructor constructor;
                Class<?>[] classArray = new Class<?>[constructorParamTypeList.size()];
                constructor = ReflectionUtils.findConstructor(beanClass, constructorParamTypeList.toArray(classArray));

                Object[] objectArray = new Object[classArray.length];
                for (int i = 0; i < classArray.length; i++) {
                    Object object = classArray[i].newInstance();
                    objectArray[i] = object;
                }          
                    // set Bean Instance for BeanDefinition
                    bean = constructor.newInstance(objectArray);            
            }


            for (PropertyValue propertyValue : fieldDefinitionList) {
                if (!propertyValue.isConstructorParam()) {
                    //Add properties through this invoke method
                    BeanUtil.invokeSetterMethod(bean, propertyValue.getName(), propertyValue.getValue());
                }
            }
            //Set the Bean object to beanDefinition
            beanDefinition.setBean(bean);
            return beanDefinition;
   

   

}}
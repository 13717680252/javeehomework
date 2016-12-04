package mmvc;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class scanpackage {
	    private String basePackage;
	    private ClassLoader cl;


	    public scanpackage(String basePackage) {
	        this.basePackage = basePackage;
	        this.cl = getClass().getClassLoader();

	    }
	
	    public List<String> getFullyQualifiedClassNameList() throws IOException {
	    	List<String> nameList=new ArrayList<>();
	        return doScan(basePackage, nameList);
	    }

	    private List<String> doScan(String basePackage, List<String> nameList) throws IOException {

	        String splashPath = StringUtil.dotToSplash(basePackage);

	        URL url = cl.getResource(splashPath);
	        String filePath = StringUtil.getRootPath(url);

	  
	        List<String> names = null; 
	        names = readFromDirectory(filePath);

	        for (String name : names) {
	            if (isClassFile(name)) {

	                nameList.add(toFullyQualifiedName(name, basePackage));
	            } else {	         
	                doScan(basePackage + "." + name, nameList);
	            }
	        }
	        return nameList;
	    }

	
	    private String toFullyQualifiedName(String shortName, String basePackage) {
	        StringBuilder sb = new StringBuilder(basePackage);
	        sb.append('.');
	        sb.append(StringUtil.trimExtension(shortName));

	        return sb.toString();
	    }


	    private List<String> readFromDirectory(String path) {
	        File file = new File(path);
	        String[] names = file.list();
	        if (null == names) {
	            return null;
	        }

	        return Arrays.asList(names);
	    }

	    private boolean isClassFile(String name) {
	        return name.endsWith(".class");
	    }
}
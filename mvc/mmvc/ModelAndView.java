package mmvc;

import java.util.HashMap;
import java.util.Set;

public class ModelAndView {
    private String viewName;
    private HashMap<String,Object> objectMap=new HashMap<>();
    private HashMap<String, Object> paramMap=new HashMap<>();

    public void setViewName(String viewName) {
        this.viewName = "/"+viewName+".jsp";
    }

    public String getViewName()
    {
        return viewName;
    }

    public void addObject(String Key, Object Value) {
        objectMap.put(Key,Value);
    }

    public Set<String> getObjectNames()
    {
        return objectMap.keySet();
    }

    public Object getObject(String Key)
    {
        return objectMap.get(Key);
    }

    public void setParamMap(HashMap<String,Object> Map)
    {
        this.paramMap=Map;
    }

    public Object getMap(String key)
    {
        return paramMap.get(key);
    }
}
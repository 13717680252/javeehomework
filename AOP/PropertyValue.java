

public class PropertyValue {
	private boolean isConstructorParam;
	public PropertyValue(String name, Object value,boolean isConstructorParam) {
		this.name = name;
		this.value = value;
		this.isConstructorParam=isConstructorParam;
	}
	private String name;	
	private Object value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isConstructorParam() {
		return isConstructorParam;
	}

	public void setConstructorParam(boolean isConstructorParam) {
		this.isConstructorParam = isConstructorParam;
	}

}
package parser.ast;

public class DynamicType implements Type {
	private final String value;
	
	public DynamicType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean isPrimitive() {
		return false;
	}

}

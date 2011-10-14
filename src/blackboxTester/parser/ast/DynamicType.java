package blackboxTester.parser.ast;

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
	
	public boolean equals(Object other) {
		return (other instanceof DynamicType) && 
		((DynamicType)other).getValue().equals(this.value);
	}

	public int hashCode() {
		return value.hashCode();
	}

}

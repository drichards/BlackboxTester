package blackboxTester.parser.ast;

public class RHSInteger implements RHS {
	private int value;
	
	public RHSInteger(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean isVariable() {
		return false;
	}
}

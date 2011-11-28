package blackboxTester.parser.ast;

public class Variable implements Term, RHS {
	private String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

package blackboxTester.parser.ast;

public class Variable implements Term {
	private String name;
	
	public Variable(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}

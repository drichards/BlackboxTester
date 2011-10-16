package blackboxTester.parser.ast;

import java.util.ArrayList;

/**
 * An abstract syntax tree representing the signatures in the parsed input.
 */
public class Signature {
	/**
	 * The type of the signature.
	 */
	private final String typename;
	/**
	 * The list of OpSpecs in the signature.
	 */
	private final ArrayList<OpSpec> opSpecs;
	
	/**
	 * Create a new Signature with the given typename and opSpecs.
	 * 
	 * @param typename
	 * @param opSpecs
	 */
	public Signature(String typename, ArrayList<OpSpec> opSpecs) {
		this.typename = typename;
		this.opSpecs = opSpecs;
	}
	
	/**
	 * @return The typename of this Signtaure.
	 */
	public String getTypename() {
		return typename;
	}
	
	/**
	 * @return The list of OpSpecs for this Signature.
	 */
	public ArrayList<OpSpec> getOpSpecs() {
		return opSpecs;
	}
}

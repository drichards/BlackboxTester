package blackboxTester.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import blackboxTester.parser.ast.Input;
import blackboxTester.parser.generated.InputParser;
import blackboxTester.parser.generated.ParseException;

/**
 * The Parser class is the main interface into the parser module.  It provides
 * a single static method <code>parse</code> which kicks off the parsing.
 * 
 * @see Parser#parse
 */
public class Parser {
	
	/**
	 * Parse the file at the given location and return the abstract syntax
	 * tree representing it.
	 * 
	 * @param inputFileName The path to the file to parse.
	 * @return An abstract syntax tree representing the parsed input.
	 * @throws IOException Thrown when the file can't be read.
	 * @throws ParseException Thrown when there's an error parsing the input file.
	 */
    public static Input parse(String inputFileName) throws IOException, ParseException {
        File file = new File(inputFileName);
        FileInputStream inputStream = new FileInputStream(file);

        InputParser parser = new InputParser(inputStream, "UTF8");
        return parser.Input();
    }
}

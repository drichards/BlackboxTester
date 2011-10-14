package blackboxTester.parser;

import java.io.File;
import java.io.FileInputStream;

import blackboxTester.parser.ast.Input;
import blackboxTester.parser.generated.InputParser;


public class Parser {
    public static Input parse() throws Exception {
        File file = new File("assets/test.txt");
        FileInputStream inputStream = new FileInputStream(file);

        InputParser parser = new InputParser(inputStream, "UTF8");
        return parser.Input();
    }
}

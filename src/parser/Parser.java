package parser;

import java.io.File;
import java.io.FileInputStream;

import parser.ast.Input;
import parser.generated.InputParser;

public class Parser {
    public static Input parse() throws Exception {
        File file = new File("assets/test.txt");
        FileInputStream inputStream = new FileInputStream(file);

        InputParser parser = new InputParser(inputStream, "UTF8");
        return parser.Input();
    }
}

package parser;

import java.io.*;
import parser.generated.*;

public class Parser {
    public static void main(String[] args) throws Exception {
        File file = new File("assets/test.txt");
        FileInputStream inputStream = new FileInputStream(file);

        InputParser parser = new InputParser(inputStream, "UTF8");
        SimpleNode node = parser.Input();
        node.dump("");
    }
}

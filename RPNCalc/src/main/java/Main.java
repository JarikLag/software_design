import token.Token;
import token.Tokenizer;
import visitor.CalcVisitor;
import visitor.PrintVisitor;
import visitor.RPNConverterVisitor;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            Tokenizer tokenizer = new Tokenizer();
            List<Token> tokenList = tokenizer.tokenize(input);

            PrintVisitor printVisitor = new PrintVisitor(System.out);
            System.out.println("Input after tokenization: ");
            printVisitor.visit(tokenList);

            RPNConverterVisitor rpnConverterVisitor = new RPNConverterVisitor();
            rpnConverterVisitor.visit(tokenList);
            List<Token> rpn = rpnConverterVisitor.getResult();

            System.out.println("Tokens after RPN converting: ");
            printVisitor.visit(rpn);

            CalcVisitor calcVisitor = new CalcVisitor();
            calcVisitor.visit(rpn);
            int result = calcVisitor.getResult();

            System.out.println("RPN process result: ");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error while executing program: " + e.getMessage());
        }
    }
}

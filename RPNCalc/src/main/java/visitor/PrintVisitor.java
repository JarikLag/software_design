package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.io.PrintStream;
import java.util.List;

public class PrintVisitor implements TokenVisitor {
    private PrintStream printStream;

    public PrintVisitor(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void visit(NumberToken token) {
        printStream.print(token.toString());
    }

    @Override
    public void visit(BraceToken token) {
        printStream.print(token.toString());
    }

    @Override
    public void visit(OperationToken token) {
        printStream.print(token.toString());
    }

    @Override
    public void visit(List<Token> tokens) {
        for (int i = 0; i < tokens.size(); ++i) {
            Token token = tokens.get(i);
            token.accept(this);
            if (i != tokens.size() - 1) {
                printStream.print(" ");
            }
        }
        printStream.println();
    }
}

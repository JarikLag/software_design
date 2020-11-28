package visitor;

import token.BraceToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class CalcVisitor implements TokenVisitor {
    private Deque<Integer> stack;
    private int result;

    public CalcVisitor() {
        stack = new ArrayDeque<>();
        result = 0;
    }

    public int getResult() {
        return result;
    }

    @Override
    public void visit(NumberToken token) {
        stack.push(token.getValue());
    }

    @Override
    public void visit(BraceToken token) {
        throw new IllegalStateException("Brackets are not allowed when calculating RPN");
    }

    @Override
    public void visit(OperationToken token) {
        if (stack.size() < 2) {
            throw new IllegalStateException("Invalid RPN: less than 2 arguments for binary operation");
        }

        int x = stack.pop();
        int y = stack.pop();
        int cur;

        switch (token) {
            case PLUS:
                cur = x + y;
                break;
            case MINUS:
                cur = y - x;
                break;
            case MUL:
                cur = x * y;
                break;
            case DIV:
                cur = y / x;
                break;
            default:
                throw new IllegalArgumentException("Unknown OperationToken type");
        }

        stack.push(cur);
    }

    @Override
    public void visit(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }

        if (stack.size() != 1) {
            throw new IllegalStateException("Invalid RPN: not only 1 number in stack after processing");
        }

        result = stack.peek();
    }
}

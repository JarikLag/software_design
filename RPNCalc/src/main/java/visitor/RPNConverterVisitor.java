package visitor;

import token.*;

import java.util.*;

public class RPNConverterVisitor implements TokenVisitor {
    private Deque<Token> stack;
    private List<Token> result;

    public RPNConverterVisitor() {
        this.stack = new ArrayDeque<>();
        this.result = new ArrayList<>();
    }

    public List<Token> getResult() {
        return result;
    }

    @Override
    public void visit(NumberToken token) {
        result.add(token);
    }

    @Override
    public void visit(BraceToken token) {
        if (token == BraceToken.LEFT_BRACE) {
            stack.push(token);
        } else {
            while (!stack.isEmpty()) {
                Token lastToken = stack.peek();
                if (lastToken == BraceToken.LEFT_BRACE) {
                    stack.pop();
                    break;
                } else if (lastToken instanceof OperationToken) {
                    result.add(lastToken);
                    stack.pop();
                } else {
                    throw new IllegalStateException("Unexpected tokens in stack: " + Arrays.toString(stack.toArray()));
                }
            }
        }
    }

    @Override
    public void visit(OperationToken token) {
        while (!stack.isEmpty()) {
            Token lastToken = stack.peek();
            if (lastToken instanceof OperationToken &&
                    getPriority(token) <= getPriority((OperationToken) lastToken)) {
                result.add(lastToken);
                stack.pop();
            } else {
                break;
            }
        }
        stack.push(token);
    }

    @Override
    public void visit(List<Token> tokens) {
        for (Token token : tokens) {
            token.accept(this);
        }
        while (!stack.isEmpty()) {
            Token lastToken = stack.peek();
            if (lastToken instanceof OperationToken) {
                result.add(lastToken);
                stack.pop();
            } else {
                throw new IllegalStateException(
                        "Encountered non-OperationToken after conversion: " + Arrays.toString(stack.toArray())
                );
            }
        }
    }

    private int getPriority(OperationToken token) {
        switch (token) {
            case PLUS:
            case MINUS:
                return 0;
            case DIV:
            case MUL:
                return 1;
            default:
                throw new IllegalArgumentException("Unknown OperationToken type");
        }
    }
}

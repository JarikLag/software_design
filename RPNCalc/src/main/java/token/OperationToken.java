package token;

import visitor.TokenVisitor;

public enum OperationToken implements Token {
    PLUS, MINUS, MUL, DIV;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}

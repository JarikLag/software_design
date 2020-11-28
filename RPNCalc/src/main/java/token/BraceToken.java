package token;

import visitor.TokenVisitor;

public enum BraceToken implements Token {
    LEFT_BRACE, RIGHT_BRACE;

    @Override
    public void accept(TokenVisitor visitor) {
        visitor.visit(this);
    }
}

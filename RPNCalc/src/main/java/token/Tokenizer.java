package token;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private List<Token> tokens;
    private State curState;

    public List<Token> tokenize(String input) {
        curState = new BasicState();
        tokens = new ArrayList<>();

        for (char c : input.toCharArray()) {
            curState.process(c);
        }
        curState.handleEOF();

        if (!(curState instanceof EOFState)) {
            throw new IllegalStateException("Tokenize process ended not in EOFState");
        }

        return tokens;
    }

    private abstract class State {
        abstract void process(char c);
        abstract void handleEOF();
    }

    private class EOFState extends State {
        @Override
        void process(char c) {
            throw new UnsupportedOperationException("EOF state cannot process characters");
        }

        @Override
        void handleEOF() {
        }
    }

    private class NumericState extends State {
        private int value = 0;

        @Override
        void process(char c) {
            if (Character.isDigit(c)) {
                value = 10 * value + (c - '0');
            } else {
                Tokenizer.this.tokens.add(new NumberToken(value));
                Tokenizer.this.curState = new BasicState();
                Tokenizer.this.curState.process(c);
            }
        }

        @Override
        void handleEOF() {
            Tokenizer.this.tokens.add(new NumberToken(value));
            Tokenizer.this.curState = new EOFState();
        }
    }

    private class BasicState extends State {
        @Override
        void process(char c) {
            if (Character.isDigit(c)) {
                Tokenizer.this.curState = new NumericState();
                Tokenizer.this.curState.process(c);
                return;
            }

            switch (c) {
                case '(':
                    Tokenizer.this.tokens.add(BraceToken.LEFT_BRACE);
                    break;
                case ')':
                    Tokenizer.this.tokens.add(BraceToken.RIGHT_BRACE);
                    break;
                case '+':
                    Tokenizer.this.tokens.add(OperationToken.PLUS);
                    break;
                case '-':
                    Tokenizer.this.tokens.add(OperationToken.MINUS);
                    break;
                case '*':
                    Tokenizer.this.tokens.add(OperationToken.MUL);
                    break;
                case '/':
                    Tokenizer.this.tokens.add(OperationToken.DIV);
                    break;
                default:
                    if (!Character.isWhitespace(c)) {
                        throw new IllegalArgumentException("Unexpected character while parsing: " + c);
                    }
            }
        }

        @Override
        void handleEOF() {
            Tokenizer.this.curState = new EOFState();
        }
    }
}

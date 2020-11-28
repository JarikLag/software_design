package token;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenizerTest {
    private Tokenizer tokenizer = new Tokenizer();

    @Test
    public void simpleInputTest() {
        String input = "1 + 2";
        List<Token> actual = tokenizer.tokenize(input);
        List<Token> expected = Arrays.asList(new NumberToken(1), OperationToken.PLUS, new NumberToken(2));
        assertEquals(expected, actual);
    }

    @Test
    public void bracesTest() {
        String input = "1+(1-3)/2";
        List<Token> actual = tokenizer.tokenize(input);
        List<Token> expected = Arrays.asList(
                new NumberToken(1),
                OperationToken.PLUS,
                BraceToken.LEFT_BRACE,
                new NumberToken(1),
                OperationToken.MINUS,
                new NumberToken(3),
                BraceToken.RIGHT_BRACE,
                OperationToken.DIV,
                new NumberToken(2)
        );
        assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidInputTest() {
        String input = "1 + x * 2";
        List<Token> actual = tokenizer.tokenize(input);
    }
}

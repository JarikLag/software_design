package visitor;

import org.junit.Before;
import org.junit.Test;
import token.BraceToken;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RPNConverterVisitorTest {
    private RPNConverterVisitor rpnConverterVisitor;

    @Before
    public void prepareCalcVisitor() {
        rpnConverterVisitor = new RPNConverterVisitor();
    }

    @Test
    public void simpleTest() {
        List<Token> input = Arrays.asList(new NumberToken(1), OperationToken.PLUS, new NumberToken(2));
        List<Token> expected = Arrays.asList(new NumberToken(1), new NumberToken(2),  OperationToken.PLUS);
        rpnConverterVisitor.visit(input);
        assertEquals(expected, rpnConverterVisitor.getResult());
    }

    @Test
    public void bracketsTest() {
        List<Token> input = Arrays.asList(
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
        List<Token> expected = Arrays.asList(
                new NumberToken(1),
                new NumberToken(1),
                new NumberToken(3),
                OperationToken.MINUS,
                new NumberToken(2),
                OperationToken.DIV,
                OperationToken.PLUS
        );
        rpnConverterVisitor.visit(input);
        assertEquals(expected, rpnConverterVisitor.getResult());
    }

    @Test(expected = IllegalStateException.class)
    public void noMatchingBracketTest() {
        List<Token> input = Arrays.asList(
                BraceToken.LEFT_BRACE,
                new NumberToken(2),
                OperationToken.PLUS,
                new NumberToken(3)
        );
        rpnConverterVisitor.visit(input);
    }
}

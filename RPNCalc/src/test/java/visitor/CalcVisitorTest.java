package visitor;

import org.junit.Before;
import org.junit.Test;
import token.NumberToken;
import token.OperationToken;
import token.Token;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CalcVisitorTest {
    private CalcVisitor calcVisitor;

    @Before
    public void prepareCalcVisitor() {
        calcVisitor = new CalcVisitor();
    }

    @Test
    public void simpleTest() {
        List<Token> input = Arrays.asList(new NumberToken(1), new NumberToken(2),  OperationToken.PLUS);
        int expected = 3;
        calcVisitor.visit(input);
        assertEquals(expected, calcVisitor.getResult());
    }

    @Test
    public void complicatedTest() {
        List<Token> input = Arrays.asList(
                new NumberToken(1),
                new NumberToken(1),
                new NumberToken(3),
                OperationToken.MINUS,
                new NumberToken(2),
                OperationToken.DIV,
                OperationToken.PLUS
        );
        int expected = 0;
        calcVisitor.visit(input);
        assertEquals(expected, calcVisitor.getResult());
    }

    @Test(expected = IllegalStateException.class)
    public void missingOperandTest() {
        List<Token> input = Arrays.asList(new NumberToken(1), OperationToken.PLUS);
        calcVisitor.visit(input);
    }
}

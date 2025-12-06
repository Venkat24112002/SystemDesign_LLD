package Calculator;

public class Expression implements ArithmaticExpression{
    private Operation operation;
    private ArithmaticExpression leftExpression;
    private ArithmaticExpression rightExpression;

    public Expression(Operation operation,ArithmaticExpression leftExpression, ArithmaticExpression rightExpression) {
        this.operation = operation;
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public int evaluate() {
        int value = 0;

        switch (operation) {
            case ADD:
                value = leftExpression.evaluate() + rightExpression.evaluate();
                break;

            case SUBTRACT:
                value = leftExpression.evaluate() - rightExpression.evaluate();
                break;

            case MULTIPLY:
                value = leftExpression.evaluate() * rightExpression.evaluate();
                break;

            case DIVIDE:
                value = leftExpression.evaluate() / rightExpression.evaluate();
        }
        return value;
    }
}

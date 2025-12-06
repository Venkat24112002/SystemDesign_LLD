package Calculator;

public class Main {

    public static void main(String[] args) {
        ArithmaticExpression number5 = new Number(5);
        ArithmaticExpression number3 = new Number(3);

        ArithmaticExpression expression = new Expression(Operation.ADD, number3, number5);

        ArithmaticExpression number2 = new Number(5);
        ArithmaticExpression expression1 = new Expression(Operation.MULTIPLY, expression, number2);
        System.out.println(expression1.evaluate());
    }
}

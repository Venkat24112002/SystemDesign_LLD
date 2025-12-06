package Calculator;

public class Number implements ArithmaticExpression{
    private int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public int evaluate() {
        return number;
    }
}

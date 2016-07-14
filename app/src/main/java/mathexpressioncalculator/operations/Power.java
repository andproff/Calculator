package mathexpressioncalculator.operations;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 04.01.12
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class Power implements Operation {

    @Override
    public double calculate(double a, double b) {
        return Math.pow(a, b);
    }

    @Override
    public double getPriority() {
        return 2;
    }

    @Override
    public String toString() {
        return "^";
    }
}

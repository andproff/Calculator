package mathexpressioncalculator.operations;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 03.01.12
 * Time: 14:44
 */
public class Multiplication implements Operation {

    @Override
    public double calculate(double a, double b) {
        return a * b;
    }

    @Override
    public double getPriority() {
        return 1;
    }

    @Override
    public String toString() {
        return "*";
    }
}

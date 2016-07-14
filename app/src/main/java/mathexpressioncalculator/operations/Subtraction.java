package mathexpressioncalculator.operations;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 03.01.12
 * Time: 14:42
 */
public class Subtraction implements Operation {


    @Override
    public double calculate(double a, double b) {
        return a - b;
    }

    @Override
    public double getPriority() {
        return 0;
    }

    @Override
    public String toString() {
        return "-";
    }
}

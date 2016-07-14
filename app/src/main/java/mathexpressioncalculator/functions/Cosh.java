package mathexpressioncalculator.functions;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 07.01.12
 * Time: 12:48
 */
public class Cosh implements Function {

    @Override
    public double perform(double a) {
        return Math.cosh(a);
    }
}

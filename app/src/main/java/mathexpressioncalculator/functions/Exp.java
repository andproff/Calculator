package mathexpressioncalculator.functions;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 07.01.12
 * Time: 12:47
 */
public class Exp implements Function {

    @Override
    public double perform(double a) {
        return Math.exp(a);
    }
}

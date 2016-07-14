package mathexpressioncalculator.functions;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 06.01.12
 * Time: 15:02
 */
public class Tan implements Function {

    @Override
    public double perform(double a) {
        return Math.tan(a);
    }
}

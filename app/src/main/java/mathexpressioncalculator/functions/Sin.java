package mathexpressioncalculator.functions;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 05.01.12
 * Time: 17:28
 */
public class Sin implements Function {

    @Override
    public double perform(double a) {
        return Math.sin(a);
    }
}

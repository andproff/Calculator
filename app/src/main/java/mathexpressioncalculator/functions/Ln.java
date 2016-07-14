package mathexpressioncalculator.functions;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 06.01.12
 * Time: 15:03
 */
public class Ln implements Function {

    @Override
    public double perform(double a) {
        return Math.log(a);
    }
}

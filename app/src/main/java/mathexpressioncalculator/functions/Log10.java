package mathexpressioncalculator.functions;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 06.01.12
 * Time: 15:04
 */
public class Log10 implements Function {

    @Override
    public double perform(double a) {
        return Math.log10(a);
    }
}

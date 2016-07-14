package mathexpressioncalculator.variables;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 05.01.12
 * Time: 17:34
 */
public class Pi implements Variable {

    @Override
    public double getValue() {
        return Math.PI;
    }
}

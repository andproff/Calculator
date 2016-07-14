package mathexpressioncalculator.variables;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 06.01.12
 * Time: 14:57
 */
public class E implements Variable {

    @Override
    public double getValue() {
        return Math.E;
    }
}

package mathexpressioncalculator.variables;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 06.01.12
 * Time: 14:25
 */
public class DoubleVariable implements Variable {

    private double value;

    public DoubleVariable(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

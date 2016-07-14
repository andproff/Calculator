package mathexpressioncalculator.operations;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 03.01.12
 * Time: 14:40
 */
public interface Operation {

    public double calculate(double a, double b);

    public double getPriority();
}

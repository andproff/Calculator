package mathexpressioncalculator.operations;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 03.01.12
 * Time: 14:46
 */
public class Operations {

    public static final int MIN_PRIORITY = 0;
    public static final int MAX_PRIORITY = 2;

    private static final Operation ADDITION = new Addition();
    private static final Operation SUBTRACTION = new Subtraction();
    private static final Operation MULTIPLICATION = new Multiplication();
    private static final Operation DIVISION = new Division();
    private static final Operation POWER = new Power();

    public static Operation parseOperation(String operation) {
        if ("+".equals(operation)) {
            return ADDITION;
        } else if ("-".equals(operation)) {
            return SUBTRACTION;
        } else if ("*".equals(operation)) {
            return MULTIPLICATION;
        } else if ("/".equals(operation)) {
            return DIVISION;
        } else if ("^".equals(operation)) {
            return POWER;
        } else {
            throw new IllegalArgumentException();
        }
    }
}

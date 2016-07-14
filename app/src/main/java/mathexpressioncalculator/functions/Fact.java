package mathexpressioncalculator.functions;

/**
 * Created by vadim on 07-Jul-16.
 */
public class Fact implements Function {
    public double perform(double a) {

        int fact = 1; // this  will be the result
        for (int i = 1; i <= a; i++) {
            fact *= i;
        }
        return fact;

    }

}

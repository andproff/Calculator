package mathexpressioncalculator.functions;

import mathexpressioncalculator.Calculator;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 07.01.12
 * Time: 12:55
 */
public class SingleVariableFunction implements Function {

    private Calculator calculator = new Calculator();
    private String variableName;

    public SingleVariableFunction() {
    }

    public SingleVariableFunction(String expression, String variableName) {
        this.variableName = variableName;
        calculator.setExpression(expression);
    }

    public String getExpression() {
        return calculator.getExpression();
    }

    public void setExpression(String expression) {
        calculator.setExpression(expression);
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public double perform(double a) {
        calculator.setDoubleVariable(variableName, a);
        return calculator.getResult();
    }
}

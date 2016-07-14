package mathexpressioncalculator.variables;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 05.01.12
 * Time: 17:32
 */
public class Variables {

    private static final Variable PI = new Pi();
    private static final Variable E = new E();

    private Map<String, Variable> variables = new HashMap<String, Variable>();

    public Variable parseVariable(String variable) {
        if ("PI".equals(variable)) {
            return PI;
        } else if ("E".equals(variable)) {
            return E;
        } else {
            if (variables.containsKey(variable)) {
                return variables.get(variable);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public void setVariable(String variableName, Variable variable) {
        variables.put(variableName, variable);
    }

    public void setDoubleVariable(String variableName, double variableValue) {
        variables.put(variableName, new DoubleVariable(variableValue));
    }

    public void setX(double variableValue) {
        variables.put("x", new DoubleVariable(variableValue));
    }

    @Override
    public String toString() {
        return variables.toString();
    }
}

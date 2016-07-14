package mathexpressioncalculator;

import mathexpressioncalculator.functions.Functions;
import mathexpressioncalculator.operations.Operation;
import mathexpressioncalculator.operations.Operations;
import mathexpressioncalculator.variables.Variable;
import mathexpressioncalculator.variables.Variables;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 03.01.12
 * Time: 13:04
 */
public class Calculator {

    private static final String OPERATION_CHARACTERS = "+-*/^";
    private static final String NUMBER_CHARACTERS = "0123456789";
    private static final String LETTER_CHARACTERS = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ_";
    private static final String IGNORE_CHARACTERS = " ";

    private String expression;
    private Variables variables = new Variables();
    private LinkedList<Operation> operationList = new LinkedList<Operation>();
    private LinkedList<Double> numberList = new LinkedList<Double>();

    public Calculator() {
    }

    public Calculator(String expression) {
        this.expression = expression;
    }

    public double getResult() {
        //Parse expression
        StringBuilder token = new StringBuilder();
        int i = 0;
        boolean isIdentifier = false;
        while (i < expression.length()) {
            String character = String.valueOf(expression.charAt(i));

            if ("(".equals(character)) {
                int openBracketsCount = 0;
                int closeBracketsCount = 0;

                for (int j = i + 1; j < expression.length(); ++j) {
                    switch (expression.charAt(j)) {
                        case '(':
                            openBracketsCount++;
                            break;
                        case ')':
                            closeBracketsCount++;
                            break;
                    }
                    if (closeBracketsCount > openBracketsCount) {
                        double result = new Calculator(expression.substring(i + 1, j)).getResult();
                        //If token is identifier and current character is '(' then function found
                        if (isIdentifier && token.length() > 0) {
                            numberList.add(Functions.parseFunction(token.toString()).perform(result));
                            token = new StringBuilder();
                            isIdentifier = false;
                        } else {
                            numberList.add(result);
                        }
                        i = j;
                        break;
                    }
                }
            } else if (NUMBER_CHARACTERS.contains(character) || ".".equals(character)) {
                token.append(character);
            } else if (LETTER_CHARACTERS.contains(character)) {
                //If identifier starts with number or contains dot then throw exception
                if (token.length() > 0 && (NUMBER_CHARACTERS.contains(token.substring(0, 1)) || token.toString().contains("."))) {
                    throw new IllegalArgumentException();
                } else {
                    token.append(character);
                    isIdentifier = true;
                }
            } else if (!IGNORE_CHARACTERS.contains(character) && !OPERATION_CHARACTERS.contains(character)) {
                //If unknown character found throw exception
                throw new IllegalArgumentException();
            }

            //Add operands and operator when operator found  or only last operand when last character found
            if (OPERATION_CHARACTERS.contains(character) || i == expression.length() - 1) {
                if (token.length() > 0) {
                    if (!isIdentifier) {
                        numberList.add(Double.parseDouble(token.toString()));
                    } else {
                        numberList.add(variables.parseVariable(token.toString()).getValue());
                        isIdentifier = false;
                    }
                    token = new StringBuilder();
                    //If subtraction operation found but there is no operand, push operand 0.0
                } else if ("-".equals(character) && numberList.isEmpty()) {
                    numberList.add(0.0);
                }
                if (i != expression.length() - 1) {
                    operationList.add(Operations.parseOperation(character));
                }
            }

            i++;
        }

        /*
        System.out.println(operationList);
        System.out.println(numberList);
        */

        //Calculate expression
        for (int priority = Operations.MAX_PRIORITY; priority >= Operations.MIN_PRIORITY; priority--) {
            i = 0;
            while (i < operationList.size()) {
                Operation operation = operationList.get(i);
                if (operation.getPriority() == priority) {
                    double operand1 = numberList.get(i);
                    double operand2 = numberList.get(i + 1);
                    double result = operation.calculate(operand1, operand2);

                    /*
                    System.out.print(operand1);
                    System.out.print(operation);
                    System.out.print(operand2);
                    System.out.print("=");
                    System.out.println(result);
                    */

                    operationList.remove(i);
                    numberList.remove(i);
                    numberList.remove(i);
                    numberList.add(i, result);
                    continue;
                }
                i++;
            }
        }

        return numberList.get(0);
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public double getVariable(String variable) {
        return variables.parseVariable(variable).getValue();
    }

    public void setVariable(String variable, Variable value) {
        variables.setVariable(variable, value);
    }

    public void setDoubleVariable(String variable, double variableValue) {
        variables.setDoubleVariable(variable, variableValue);
    }

    public void setX(double variableValue) {
        variables.setX(variableValue);
    }
}
package mathexpressioncalculator.functions;

/**
 * Created by IntelliJ IDEA.
 * User: evgeniy
 * Date: 05.01.12
 * Time: 17:27
 */
public class Functions {

    private static final Function SIN = new Sin();
    private static final Function COS = new Cos();
    private static final Function TAN = new Tan();
    private static final Function ASIN = new Asin();
    private static final Function ACOS = new Acos();
    private static final Function ATAN = new Atan();
    private static final Function SINH = new Sinh();
    private static final Function COSH = new Cosh();
    private static final Function TANH = new Tanh();
    private static final Function EXP = new Exp();
    private static final Function LN = new Ln();
    private static final Function LOG10 = new Log10();

    private static final Function SQRT = new Sqrt();
    private static final Function FACT = new Fact();

    public static Function parseFunction(String function) {
        if ("sin".equals(function)) {
            return SIN;
        } else if ("cos".equals(function)) {
            return COS;
        } else if ("tan".equals(function)) {
            return TAN;
        } else if ("asin".equals(function)) {
            return ASIN;
        } else if ("acos".equals(function)) {
            return ACOS;
        } else if ("atan".equals(function)) {
            return ATAN;
        } else if ("sinh".equals(function)) {
            return SINH;
        } else if ("cosh".equals(function)) {
            return COSH;
        } else if ("tanh".equals(function)) {
            return TANH;
        } else if ("exp".equals(function)) {
            return EXP;
        } else if ("ln".equals(function)) {
            return LN;
        } else if ("log10".equals(function)) {
            return LOG10;
        } else if ("sqrt".equals(function)) {
            return SQRT;
        } else if ("fact".equals(function)) {
            return FACT;
        } else {
            throw new IllegalArgumentException();
        }
    }
}

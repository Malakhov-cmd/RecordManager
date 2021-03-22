
/**
 * Perform entered string to executable code
 *
 * @author George Malakhov
 * @version 1.0
 */
public class Resulter {
    /**
     * Result of exponentiation in one of two branch
     */
    private static double result;
    /**
     * Flag of chosen branch
     */
    private static boolean flag;
    /**
     * Executable string
     */
    private String seqStr;

    /**
     * Initialize Resulter {@link Resulter#seqStr}
     *
     * @see Resulter
     */
    public Resulter(String seqStr) {
        this.seqStr = seqStr;
    }

    /**
     * Result of executable code
     *
     * @return Result value
     */
    public static double getResult() {
        return result;
    }

    /**
     * Check witch one branch chosen
     *
     * @return true if this is the first branch
     * false if this is second branch
     */
    public static boolean isFlag() {
        return flag;
    }

    /**
     * Recording results
     *
     * @param result calculated result of exponential
     * @param flag   branch where calculating was
     *               true if this is the first branch
     *               false if this is second branch
     */
    public static void setResult(double result, boolean flag) {
        Resulter.result = result;
        Resulter.flag = flag;
        System.out.println(result + " " + flag);
    }

    /**
     * Start calculating via CreatorMachineCall library
     */
    public void start() {
        try {
            Creator.task(seqStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * Load and operate with DLL library
 *
 * @author George Malakhov
 * @version 1.0
 */
public class MultyJNI {
    /**
     Static initialize library
     * */
    static {
        System.loadLibrary("multyAssembler");
    }

    /**
     * Native assembly method present that multiply two digit
     *
     * @param x first value
     * @param y second value
     * @return Result of multiply
     */
    private native int multy(int x, int y);

    /**
     * Multiplier for call from other side
     *
     * @param x first value
     * @param y second value
     * @return Result of multiply
     */
    public int calc(int x, int y) {
        MultyJNI multyJNI = new MultyJNI();
        return multyJNI.multy(x, y);
    }
}

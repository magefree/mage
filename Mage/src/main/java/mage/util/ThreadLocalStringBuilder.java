package mage.util;

/**
 *
 * @author BetaSteward
 */
public class ThreadLocalStringBuilder extends ThreadLocal<StringBuilder> {

    private final int size;
    
    public ThreadLocalStringBuilder(int size) {
        this.size = size;
    }
    
    @Override
    protected StringBuilder initialValue() {
        return new StringBuilder(size);
    }

    @Override
    public StringBuilder get() {
        StringBuilder b = super.get();
        b.setLength(0); // clear/reset the buffer
        return b;
    }
    
}

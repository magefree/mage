package mage.util;

import java.io.Serializable;

/**
 * A helper class for facilitating the multi-choose dialog
 *
 * @author alexander-novo, JayDi85
 */
public class MultiAmountMessage implements Serializable, Copyable<MultiAmountMessage> {

    public String message;
    public int min;
    public int max;
    public int defaultValue;

    public MultiAmountMessage(String message, int min, int max) {
        this(message, min, max, min);
    }

    public MultiAmountMessage(String message, int min, int max, int defaultValue) {
        this.message = message;
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
    }

    private MultiAmountMessage(final MultiAmountMessage mes) {
        this.message = mes.message;
        this.min = mes.min;
        this.max = mes.max;
        this.defaultValue = mes.defaultValue;
    }

    @Override
    public String toString() {
        return String.format("%s - from %d to %d - default %d", message, min, max, defaultValue);
    }

    @Override
    public MultiAmountMessage copy() {
        return new MultiAmountMessage(this);
    }
}

package mage.util;

import java.io.Serializable;

// Author: alexander-novo
// A helper class for facilitating the multi-choose dialog
public class MultiAmountMessage implements Serializable {
    public String message;
    public int min;
    public int max;

    public MultiAmountMessage(String message, int min, int max) {
        this.message = message;
        this.min = min;
        this.max = max;
    }
}

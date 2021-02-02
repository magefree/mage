package mage.filter;

import java.io.Serializable;

/**
 * @author nantuko
 */
public class FilterMana implements Serializable {

    protected boolean black;
    protected boolean green;
    protected boolean white;
    protected boolean red;
    protected boolean blue;
    protected boolean generic;
    protected boolean colorless;

    public FilterMana() {
    }

    public FilterMana(FilterMana filter) {
        black = filter.black;
        green = filter.green;
        white = filter.white;
        red = filter.red;
        blue = filter.blue;
        generic = filter.generic;
        colorless = filter.colorless;
    }

    public boolean isBlack() {
        return black;
    }

    public void setBlack(boolean black) {
        this.black = black;
    }

    public boolean isGreen() {
        return green;
    }

    public void setGreen(boolean green) {
        this.green = green;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public boolean isGeneric() {
        return generic;
    }

    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    public boolean isColorless() {
        return !(white || blue || black || red || green) || colorless;
    }

    public void setColorless(boolean colorless) {
        this.colorless = colorless;
    }

    public boolean isMulticolored() {
        return getColorCount() > 1;
    }

    public int getColorCount() {
        int colorCount = 0;
        if (this.white) {
            colorCount += 1;
        }
        if (this.blue) {
            colorCount += 1;
        }
        if (this.black) {
            colorCount += 1;
        }
        if (this.red) {
            colorCount += 1;
        }
        if (this.green) {
            colorCount += 1;
        }
        return colorCount;
    }

    public FilterMana copy() {
        return new FilterMana(this);
    }

    @Override
    public String toString() {
        // wubrg order
        return (white ? "{W}" : "")
                + (blue ? "{U}" : "")
                + (black ? "{B}" : "")
                + (red ? "{R}" : "")
                + (green ? "{G}" : "");
    }
}

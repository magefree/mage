package mage.filter;

import mage.ObjectColor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public void addAll(FilterMana filterMana) {
        if (filterMana.white) {
            this.white = true;
        }
        if (filterMana.blue) {
            this.blue = true;
        }
        if (filterMana.black) {
            this.black = true;
        }
        if (filterMana.red) {
            this.red = true;
        }
        if (filterMana.green) {
            this.green = true;
        }
    }

    public void removeAll(FilterMana filterMana) {
        if (filterMana.white) {
            this.white = false;
        }
        if (filterMana.blue) {
            this.blue = false;
        }
        if (filterMana.black) {
            this.black = false;
        }
        if (filterMana.red) {
            this.red = false;
        }
        if (filterMana.green) {
            this.green = false;
        }
    }

    public List<ObjectColor> getColors() {
        List<ObjectColor> colors = new ArrayList<>();
        if (this.white) {
            colors.add(ObjectColor.WHITE);
        }
        if (this.blue) {
            colors.add(ObjectColor.BLUE);
        }
        if (this.black) {
            colors.add(ObjectColor.BLACK);
        }
        if (this.red) {
            colors.add(ObjectColor.RED);
        }
        if (this.green) {
            colors.add(ObjectColor.GREEN);
        }
        return colors;
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

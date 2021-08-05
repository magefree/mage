package mage.cards;

import java.awt.*;

/**
 * Inner our outer/draw spaces in cards (example: if you want add free space for animation for icons drawing)
 *
 * @author JayDi85
 */
public class MageCardSpace {

    public static final MageCardSpace empty = new MageCardSpace(0, 0, 0, 0);

    int left;
    int right;
    int top;
    int bottom;

    Color debugColor = null; // add colored border for draw debug

    public MageCardSpace(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public MageCardSpace withDebugColor(Color color) {
        this.debugColor = color;
        return this;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }

    public int getBottom() {
        return bottom;
    }

    public int getWidth() {
        return this.left + this.right;
    }

    public int getHeight() {
        return this.top + this.bottom;
    }

    public Color getDebugColor() {
        return debugColor;
    }

    /**
     * Creates combined space (sums all values from each space and clear a debug color)
     *
     * @param spaces list of spaces to combine
     * @return
     */
    public static MageCardSpace combine(MageCardSpace... spaces) {
        MageCardSpace res = new MageCardSpace(0, 0, 0, 0);
        for (MageCardSpace space : spaces) {
            res.left += space.left;
            res.right += space.right;
            res.top += space.top;
            res.bottom += space.bottom;
        }
        return res;
    }
}

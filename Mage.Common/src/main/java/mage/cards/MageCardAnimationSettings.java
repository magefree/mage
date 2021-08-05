package mage.cards;

import java.awt.*;

/**
 * Contains animation/scale settings to send from card panel to parent panel
 *
 * @author JayDi85
 */
public class MageCardAnimationSettings {

    private boolean visible = true;

    private double translateX = 0;
    private double translateY = 0;

    private double scaleX = 0;
    private double scaleY = 0;

    private double rotateTheta = 0;
    private double rotateX = 0;
    private double rotateY = 0;

    public MageCardAnimationSettings withVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public MageCardAnimationSettings withTranslate(double x, double y) {
        this.translateX = x;
        this.translateY = y;
        return this;
    }

    public MageCardAnimationSettings withScale(double x, double y) {
        this.scaleX = x;
        this.scaleY = y;
        return this;
    }

    public MageCardAnimationSettings withRotate(double rotateTheta, double x, double y) {
        this.rotateTheta = rotateTheta;
        this.rotateX = x;
        this.rotateY = y;
        return this;
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * Move the position (uses for animation/rotation in multi layer cards)
     *
     * @param offsetX
     * @param offsetY
     */
    public void applyOffsets(int offsetX, int offsetY) {

        // translate
        if (translateX != 0 || translateY != 0) {
            translateX += offsetX;
            translateY += offsetY;
        }

        // scale don't have offsets

        // rotate
        if (rotateTheta != 0 || rotateX != 0 || rotateY != 0) {
            rotateX += offsetX;
            rotateY += offsetY;
        }
    }

    /**
     * Transform draw context (example: card rotate)
     * Draw settings calculates by child panel, but drawing and animation goes from parent, so you must use scale factor
     *
     * @param g2d graphic context of the card's top layer
     */
    public void doTransforms(Graphics2D g2d) {
        if (!visible) {
            return;
        }

        double factorX = 1;
        double factorY = 1;

        // translate
        if (translateX != 0 || translateY != 0) {
            g2d.translate(translateX * factorX, translateY * factorY);
        }

        // scale
        if (scaleX != 0 || scaleY != 0) {
            g2d.scale(scaleX * factorX, scaleY * factorY);
        }

        // rotate
        if (rotateTheta != 0 || rotateX != 0 || rotateY != 0) {
            g2d.rotate(rotateTheta, rotateX * factorX, rotateY * factorY); // rotateTheta don't have scale factor
        }
    }
}

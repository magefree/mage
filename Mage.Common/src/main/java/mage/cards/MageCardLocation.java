package mage.cards;

import java.awt.*;

/**
 * Card's location with real component coords and sizes (can be related to screen or related to parent panel)
 *
 * @author JayDi85
 */
public class MageCardLocation {

    final private Rectangle componentRect;
    final private Rectangle cardRect;

    public MageCardLocation(Point componentLocation, MageCardSpace cardOuterSpace, Rectangle componentBounds) {
        this.componentRect = new Rectangle(componentLocation.x, componentLocation.y, componentBounds.width, componentBounds.height);
        this.cardRect = new Rectangle(
                this.componentRect.x + cardOuterSpace.left,
                this.componentRect.y + cardOuterSpace.top,
                this.componentRect.width - cardOuterSpace.getWidth(),
                this.componentRect.height - cardOuterSpace.getHeight()
        );
    }

    public int getCardX() {
        return cardRect.x;
    }

    public int getCardY() {
        return cardRect.y;
    }

    /**
     * Card's coord for relative calcs (0,0 is component's top left corner)
     *
     * @return
     */
    public int getCardRelativeX() {
        return cardRect.x - componentRect.x;
    }

    /**
     * Card's coord for relative calcs (0,0 is component's top left corner)
     *
     * @return
     */
    public int getCardRelativeY() {
        return cardRect.y - componentRect.y;
    }

    public int getCardWidth() {
        return cardRect.width;
    }

    public int getCardHeight() {
        return cardRect.height;
    }

    public Point getCardPoint() {
        return new Point(this.cardRect.x, this.cardRect.y);
    }

    public int getComponentX() {
        return componentRect.x;
    }

    public int getComponentY() {
        return componentRect.y;
    }

    public int getComponentWidth() {
        return componentRect.width;
    }

    public int getComponentHeight() {
        return componentRect.height;
    }

    public Rectangle getCardBounds() {
        // must be copy
        return new Rectangle(this.cardRect);
    }

    public Rectangle getComponentBounds() {
        // must be copy
        return new Rectangle(this.componentRect);
    }
}

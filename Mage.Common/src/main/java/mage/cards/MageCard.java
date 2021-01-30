package mage.cards;

import mage.constants.Zone;
import mage.view.CardView;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author JayDi85
 */
public abstract class MageCard extends JLayeredPane {

    private static final Logger logger = Logger.getLogger(MageCard.class);

    /**
     * Return real MageCard panel (example: card icons uses MageLayerCard to implement additional panels)
     * <p>
     * If you need callback from card then use CardEventSource from form (search for example, e.g. card clicks processing)
     *
     * @return
     */
    public MageCard getMainPanel() {
        return this;
    }

    private static final long serialVersionUID = 6089945326434301879L;

    public abstract void onBeginAnimation();

    public abstract void onEndAnimation();

    public abstract boolean isTapped();

    public abstract boolean isFlipped();

    public abstract void setAlpha(float transparency);

    public abstract float getAlpha();

    public abstract CardView getOriginal();

    // sets the vertical text offset for the card name on the image, use to move caption to card center
    public abstract void setCardCaptionTopOffset(int yOffsetPercent);

    public abstract void setCardBounds(int x, int y, int width, int height);

    public abstract void update(CardView card);

    public abstract void updateArtImage();

    public abstract Image getImage();

    public abstract void setZone(Zone zone);

    public abstract Zone getZone();

    public abstract void toggleTransformed();

    public abstract boolean isTransformed();

    public abstract void showCardTitle();

    public abstract void setSelected(boolean selected);

    /**
     * Set link to cards list panel that contains the card component (can process mouse events from the card)
     *
     * @param cardContainer
     */
    public abstract void setCardContainerRef(Container cardContainer);

    public abstract void setTopPanelRef(MageCard mageCard);

    public abstract MageCard getTopPanelRef();

    public abstract Container getCardContainer();

    public abstract void setChoosable(boolean isChoosable);

    public abstract boolean isChoosable();

    public abstract void setPopupMenu(JPopupMenu popupMenu);

    public abstract JPopupMenu getPopupMenu();

    public abstract void cleanUp();

    public abstract int getCardWidth();

    public abstract int getCardHeight();

    public abstract MageCardAnimationSettings getAnimationSettings(int offsetX, int offsetY, float cardBoundWidth, float cardBoundHeight);

    public abstract List<MageCard> getLinks();

    public abstract MageCardSpace getOuterSpace();

    /**
     * Return top layer component location without outer/draw space (real card)
     *
     * @return
     */
    public MageCardLocation getCardLocation() {
        return getTopPanelRef().getCardLocation();
    }

    /**
     * Set card location (top left corner of the main card panel). All calls goes to top layer panel.
     *
     * @param x
     * @param y
     */
    public void setCardLocation(int x, int y) {
        setLocation(x, y);
    }

    /**
     * Return top layer component location without outer/draw space (real card)
     *
     * @return
     */
    public MageCardLocation getCardLocationOnScreen() {
        return getTopPanelRef().getCardLocationOnScreen();
    }

    @Override
    @Deprecated // default getLocationOnScreen for Swing engine only, use getCardLocationOnScreen instead
    public Point getLocationOnScreen() {
        return super.getLocationOnScreen();
    }

    @Override
    @Deprecated // default getLocation for Swing engine only, use getCardLocation instead
    public Point getLocation() {
        return super.getLocation();
    }

    @Override
    @Deprecated // default setLocation for inner usage only, call setCardLocation instead
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
    }

    @Override
    @Deprecated // default setLocation for inner usage only, call setCardLocation instead
    public void setLocation(Point p) {
        super.setLocation(p);
    }

    @Override
    @Deprecated // default getBounds for Swing engine only, use getCardLocation instead
    public Rectangle getBounds() {
        return super.getBounds();
    }

    @Override
    @Deprecated // default setBounds for inner usage only, call setCardBounds instead
    public void setBounds(Rectangle r) {
        super.setBounds(r);
    }

    @Override
    @Deprecated // default setBounds for inner usage only, call setCardBounds instead
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
    }

    @Override
    @Deprecated // default getHeight for inner usage only, call getCardLocation instead
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    @Deprecated // default getWidth for inner usage only, call getCardLocation instead
    public int getWidth() {
        return super.getWidth();
    }

    @Override
    @Deprecated // default getSize for inner usage only, call getCardLocation.getCardWidth instead
    public Dimension getSize() {
        return super.getSize();
    }
}

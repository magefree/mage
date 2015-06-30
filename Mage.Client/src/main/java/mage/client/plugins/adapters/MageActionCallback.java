package mage.client.plugins.adapters;

import mage.cards.MageCard;
import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.components.MageComponents;
import mage.client.dialog.PreferencesDialog;
import mage.client.plugins.impl.Plugins;
import mage.client.util.DefaultActionCallback;
import mage.client.util.ImageHelper;
import mage.client.util.gui.ArrowBuilder;
import mage.client.util.gui.ArrowUtil;
import mage.client.util.gui.GuiDisplayUtil;
import mage.components.CardInfoPane;
import mage.constants.EnlargeMode;
import mage.remote.Session;
import mage.utils.ThreadUtils;
import mage.view.CardView;
import mage.view.PermanentView;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXPanel;
import org.mage.card.arcane.CardPanel;
import org.mage.plugins.card.images.ImageCache;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Class that handles the callbacks from the card panels to mage to display big card
 * images from the cards the mouse hovers on. Also handles tooltip text window.
 *
 * @author Nantuko, noxx
 */
public class MageActionCallback implements ActionCallback {

    private static final Logger logger = Logger.getLogger(ActionCallback.class);
    public static final int GAP_X = 5;
    public static final double COMPARE_GAP_X = 30;

    public static final int GO_DOWN_ON_DRAG_Y_OFFSET = 0;
    public static final int GO_UP_ON_DRAG_Y_OFFSET = 0;

    public static final int MIN_X_OFFSET_REQUIRED = 20;

    private Popup popup;
    private JPopupMenu jPopupMenu;
    private BigCard bigCard;
    protected static final DefaultActionCallback defaultCallback = DefaultActionCallback.getInstance();
    protected static Session session = MageFrame.getSession();
    private CardView popupCard;
    private TransferData popupData;
    private JComponent cardInfoPane;
    private volatile boolean popupTextWindowOpen = false;
    enum EnlargedWindowState {
        CLOSED, NORMAL, ROTATED
    }
    private volatile EnlargedWindowState enlargedWindowState = EnlargedWindowState.CLOSED;
    //private volatile boolean enlargedImageWindowOpen = false;
    // shows the alternative card the normal card or the alternative card (copy source, other flip side, other transformed side)
    private volatile EnlargeMode enlargeMode;

    private static final ScheduledExecutorService timeoutExecutor = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> hideTimeout;

    private CardPanel prevCard;
    private boolean startedDragging;
    private boolean isDragging;
    private Point initialCardPos;
    private Point initialMousePos;
    private Set<CardPanel> cardPanels = new HashSet<CardPanel>();

    public MageActionCallback() {
        enlargeMode = EnlargeMode.NORMAL;
    }

    public void setCardPreviewComponent(BigCard bigCard) {
        this.bigCard = bigCard;
    }

    public synchronized void refreshSession() {
        if (session == null) {
            session = MageFrame.getSession();
        }
        if (cardInfoPane == null) {
            cardInfoPane = Plugins.getInstance().getCardInfoPane();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e, TransferData data) {
    }

    @Override
    public void mouseEntered(MouseEvent e, final TransferData data) {
        hidePopup();
        cancelTimeout();

        this.popupCard = data.card;
        this.popupData = data;

        Component parentComponent = SwingUtilities.getRoot(data.component);
        Point parentPoint = parentComponent.getLocationOnScreen();

        if (data.locationOnScreen == null) {
            data.locationOnScreen = data.component.getLocationOnScreen();
        }

        ArrowUtil.drawArrowsForTargets(data, parentPoint);
        ArrowUtil.drawArrowsForSource(data, parentPoint);
        ArrowUtil.drawArrowsForPairedCards(data, parentPoint);
        ArrowUtil.drawArrowsForEnchantPlayers(data, parentPoint);

        showPopup(data, parentComponent, parentPoint);
    }

    private void showPopup(final TransferData data, final Component parentComponent, final Point parentPoint) {
        if (data.component != null) {
            String showTooltips = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_TOOLTIPS_ANY_ZONE, "true");
            if (showTooltips.equals("false")) {
                return;
            }
        }

        if (cardInfoPane == null) {
            PopupFactory factory = PopupFactory.getSharedInstance();
            if (data.locationOnScreen == null) {
                if (data.component == null) {
                    return;
                }
                data.locationOnScreen = data.component.getLocationOnScreen();
            }
            popup = factory.getPopup(data.component, data.popupText, (int) data.locationOnScreen.getX() + data.popupOffsetX, (int) data.locationOnScreen.getY() + data.popupOffsetY + 40);
            popup.show();
            // hack to get popup to resize to fit text
            popup.hide();
            popup = factory.getPopup(data.component, data.popupText, (int) data.locationOnScreen.getX() + data.popupOffsetX, (int) data.locationOnScreen.getY() + data.popupOffsetY + 40);
            popup.show();
        } else {
            sumbitShowPopupTask(data, parentComponent, parentPoint);
        }
    }

    private void sumbitShowPopupTask(final TransferData data, final Component parentComponent, final Point parentPoint) {
        ThreadUtils.threadPool2.submit(new Runnable() {
            @Override
            public void run() {
                ThreadUtils.sleep(300);

                if (popupCard == null || !popupCard.equals(data.card) || session == null || !popupTextWindowOpen || !enlargedWindowState.equals(EnlargedWindowState.CLOSED)) {
                    return;
                }

                try {
                    final Component popupContainer = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
                    Component popup2 = MageFrame.getUI().getComponent(MageComponents.CARD_INFO_PANE);

                    ((CardInfoPane) popup2).setCard(data.card, popupContainer);

                    if (data.locationOnScreen == null) {
                        data.locationOnScreen = data.component.getLocationOnScreen();
                    }

                    Point location = new Point((int) data.locationOnScreen.getX() + data.popupOffsetX - 40, (int) data.locationOnScreen.getY() + data.popupOffsetY - 40);
                    location = GuiDisplayUtil.keepComponentInsideParent(location, parentPoint, popup2, parentComponent);
                    location.translate(-parentPoint.x, -parentPoint.y);
                    popupContainer.setLocation(location);

                    ThreadUtils.sleep(200);

                    final Component c = MageFrame.getUI().getComponent(MageComponents.DESKTOP_PANE);
                    SwingUtilities.invokeLater(new Runnable() {
                                                   @Override
                                                   public void run() {
                                                       if (!popupTextWindowOpen || !enlargedWindowState.equals(EnlargedWindowState.CLOSED)) {
                                                           return;
                                                       }
                                                       popupContainer.setVisible(true);
                                                       c.repaint();
                                                   }
                                               }
                    );

                } catch (InterruptedException e) {
                    logger.warn(e.getMessage());
                }
            }
        });
    }

    @Override
    public void mousePressed(MouseEvent e, TransferData data) {
        data.component.requestFocusInWindow();

        // for some reason sometime mouseRelease happens before numerous Mouse_Dragged events
        // that results in not finished dragging
        clearDragging(this.prevCard);

        isDragging = false;
        startedDragging = false;
        prevCard = null;
        cardPanels.clear();
        Point mouse = new Point(e.getX(), e.getY());
        SwingUtilities.convertPointToScreen(mouse, data.component);
        initialMousePos = new Point((int)mouse.getX(), (int)mouse.getY());
        initialCardPos = data.component.getLocation();
        // Closes popup & enlarged view if a card/Permanent is selected
        hidePopup();
    }

    @Override
    public void mouseReleased(MouseEvent e, TransferData transferData) {
        CardPanel card = ((CardPanel)transferData.component);
        if (card.getZone() != null && card.getZone().equalsIgnoreCase("hand")) {
            int maxXOffset = 0;
            if (isDragging) {
                Point mouse = new Point(e.getX(), e.getY());
                SwingUtilities.convertPointToScreen(mouse, transferData.component);
                maxXOffset = Math.abs((int) (mouse.getX() - initialMousePos.x));
            }

            clearDragging(card);

            this.startedDragging = false;
            if (maxXOffset < MIN_X_OFFSET_REQUIRED) { // we need this for protection from small card movements
                transferData.component.requestFocusInWindow();
                defaultCallback.mouseClicked(e, transferData.gameId, session, transferData.card);
                // Closes popup & enlarged view if a card/Permanent is selected
                hidePopup();
            }
        } else {
            transferData.component.requestFocusInWindow();
            defaultCallback.mouseClicked(e, transferData.gameId, session, transferData.card);
            // Closes popup & enlarged view if a card/Permanent is selected
            hidePopup();
        }
    }

    private void clearDragging(CardPanel card) {
        if (this.startedDragging && prevCard != null && card != null) {
            for (Component component : card.getCardArea().getComponents()) {
                if (component instanceof CardPanel) {
                    if (cardPanels.contains(component)) {
                        component.setLocation(component.getLocation().x, component.getLocation().y - GO_DOWN_ON_DRAG_Y_OFFSET);
                    }
                }
            }
            card.setLocation(card.getLocation().x, card.getLocation().y + GO_UP_ON_DRAG_Y_OFFSET);
            sort(card, card.getCardArea(), true);
            cardPanels.clear();
        }
        prevCard = null;
    }

    @Override
    public void mouseMoved(MouseEvent e, TransferData transferData) {
        handlePopup(transferData);
    }

    @Override
    public void mouseDragged(MouseEvent e, TransferData transferData) {
        CardPanel card = ((CardPanel)transferData.component);
        if (card.getZone() == null || !card.getZone().equalsIgnoreCase("hand")) {
            // drag'n'drop is allowed for HAND zone only
            return;
        }
        isDragging = true;
        prevCard = card;
        Point p = card.getCardLocation();
        Point mouse = new Point(e.getX(), e.getY());
        SwingUtilities.convertPointToScreen(mouse, transferData.component);
        int xOffset = card.getXOffset(card.getCardWidth());
        int newX = Math.max(initialCardPos.x + (int)(mouse.getX() - initialMousePos.x) - xOffset, 0);
        card.setCardBounds(
                newX,
                p.y,
                card.getCardWidth(),
                card.getCardHeight());
        card.getCardArea().setComponentZOrder(card, 0);
        sort(card, card.getCardArea(), false);

        if (!this.startedDragging) {
            this.startedDragging = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e, final TransferData data) {
        if (data != null) {
            hideAll(data.gameId);
        } else {
            hideAll(null);
        }
        ///clearDragging((CardPanel)data.component);
    }

    private void sort(CardPanel card, JPanel container, boolean sortSource) {
        java.util.List<CardPanel> cards = new ArrayList<>();
        for (Component component : container.getComponents()) {
            if (component instanceof CardPanel) {
                if (!component.equals(card)) {
                    if (!cardPanels.contains(component)) {
                        component.setLocation(component.getLocation().x, component.getLocation().y + GO_DOWN_ON_DRAG_Y_OFFSET);
                    }
                    cardPanels.add((CardPanel)component);
                } else {
                    if (!startedDragging) {
                        component.setLocation(component.getLocation().x, component.getLocation().y - GO_UP_ON_DRAG_Y_OFFSET);
                    }
                }
                cards.add((CardPanel)component);
            }
        }
        sortLayout(cards, card, sortSource);
    }

    private void sortLayout(List<CardPanel> cards, CardPanel source, boolean includeSource) {
        source.getLocation().x -= COMPARE_GAP_X; // this creates nice effect

        Collections.sort(cards, new Comparator<CardPanel>() {
            @Override
            public int compare(CardPanel cp1, CardPanel cp2) {
                return Integer.valueOf(cp1.getLocation().x).compareTo(cp2.getLocation().x);
            }
        });

        int dx = 0;
        boolean createdGapForSource = false;
        for (Component component : cards) {
            if (!includeSource) {
                if (!component.equals(source)) {
                    component.setLocation(dx, component.getLocation().y);
                    dx += ((CardPanel) component).getCardWidth() + GAP_X;
                    // once dx is bigger than source's x position
                    // we need to create a gap for the source card
                    // but only once
                    if (!createdGapForSource && (dx + COMPARE_GAP_X) > source.getLocation().x) {
                        createdGapForSource = true;
                        dx += ((CardPanel) component).getCardWidth() + GAP_X;
                    }
                }
            } else {
                component.setLocation(dx, component.getLocation().y);
                dx += ((CardPanel) component).getCardWidth() + GAP_X;
            }
        }
    }

    private void handlePopup(TransferData transferData) {
        if (!Plugins.getInstance().isCardPluginLoaded()) {
            return;
        }
        if (bigCard == null) {
            return;
        }

        MageCard mageCard = (MageCard) transferData.component;
        if (!popupTextWindowOpen || mageCard.getOriginal().getId() != bigCard.getCardId()) {
            if (bigCard.getWidth() > 0) {
                synchronized (MageActionCallback.class) {
                    if (!popupTextWindowOpen || mageCard.getOriginal().getId() != bigCard.getCardId()) {
                        if (!popupTextWindowOpen) {
                            bigCard.resetCardId();
                        }
                        popupTextWindowOpen = true;
                        Image image = mageCard.getImage();
                        displayCardInfo(mageCard, image, bigCard);
                    }
                }
            } else {
                popupTextWindowOpen = true;
            }
            if (!enlargedWindowState.equals(EnlargedWindowState.CLOSED)) {
                displayEnlargedCard(mageCard.getOriginal(), transferData);
            }
        }
    }

    /**
     * Hides the text popup window
     *
     */
    @Override
    public void hidePopup() {
        this.popupCard = null;
        if (popup != null) {
            popup.hide();
        }
        if (jPopupMenu != null) {
            jPopupMenu.setVisible(false);
        }
        try {
            if (session == null) {
                return;
            }
            // set enlarged card display to visible = false
            Component popupContainer = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
            popupContainer.setVisible(false);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void hideGameUpdate(UUID gameId) {
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.TARGET);
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.PAIRED);
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.SOURCE);
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.ENCHANT_PLAYERS);
    }

    public void hideAll(UUID gameId) {
        hidePopup();
        startHideTimeout();
        this.popupTextWindowOpen = false;
        if (gameId != null) {
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.TARGET);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.PAIRED);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.SOURCE);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.ENCHANT_PLAYERS);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, TransferData transferData) {
        int notches = e.getWheelRotation();
        if (!enlargedWindowState.equals(EnlargedWindowState.CLOSED)) {
            // same move direction will be ignored, opposite direction closes the enlarged window
            if (enlargeMode.equals(EnlargeMode.NORMAL)) {
                if (notches > 0) {
                    hideEnlargedCard();
                }
            } else {
                if (notches < 0) {
                    hideEnlargedCard();
                }
            }
            return;
        }
        if (notches < 0) {
            // move up - show normal image
            enlargeCard(EnlargeMode.NORMAL);
        } else {
            // move down - show alternate image
            enlargeCard(EnlargeMode.ALTERNATE);
        }
    }

    /**
     * Show the big card image on mouse position while hovering over a card
     *
     * @param showAlternative defines if the original image (if it's a copied card) or the opposite side of a transformable card will be shown
     */
    public void enlargeCard(EnlargeMode showAlternative) {
        if (enlargedWindowState.equals(EnlargedWindowState.CLOSED)) {
            this.enlargeMode = showAlternative;
            CardView cardView = null;
            if (popupData != null) {
                cardView = popupData.card;
            }
            if (this.popupTextWindowOpen) {
                hidePopup();
            }
            if (cardView != null) {
                if (cardView.isToRotate()) {
                    enlargedWindowState = EnlargedWindowState.ROTATED;
                } else {
                    enlargedWindowState = EnlargedWindowState.NORMAL;
                }
                displayEnlargedCard(cardView, popupData);
            }
        }
    }

    public void hideEnlargedCard() {
        if (!enlargedWindowState.equals(EnlargedWindowState.CLOSED)) {
             enlargedWindowState = EnlargedWindowState.CLOSED;
            try {
                Component cardPreviewContainer = MageFrame.getUI().getComponent(MageComponents.CARD_PREVIEW_CONTAINER);
                cardPreviewContainer.setVisible(false);
                cardPreviewContainer = MageFrame.getUI().getComponent(MageComponents.CARD_PREVIEW_CONTAINER_ROTATED);
                cardPreviewContainer.setVisible(false);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    private void displayEnlargedCard(final CardView cardView, final TransferData transferData) {
        ThreadUtils.threadPool2.submit(new Runnable() {
            @Override
            public void run() {
                if (cardView == null) {
                    return;
                }
                try {
                    if (enlargedWindowState.equals(EnlargedWindowState.CLOSED)) {
                        return;
                    }
                    Component parentComponent = SwingUtilities.getRoot(transferData.component);
                    Point parentPoint = parentComponent.getLocationOnScreen();

                    MageComponents mageComponentCardPreviewContainer;
                    MageComponents mageComponentCardPreviewPane;
                    if (cardView.isToRotate()) {
                        if(enlargedWindowState.equals(EnlargedWindowState.NORMAL)) {
                            hideEnlargedCard();
                            enlargedWindowState = EnlargedWindowState.ROTATED;
                        }
                        mageComponentCardPreviewContainer = MageComponents.CARD_PREVIEW_CONTAINER_ROTATED;
                        mageComponentCardPreviewPane = MageComponents.CARD_PREVIEW_PANE_ROTATED;
                    } else {
                        if(enlargedWindowState.equals(EnlargedWindowState.ROTATED)) {
                            hideEnlargedCard();
                            enlargedWindowState = EnlargedWindowState.NORMAL;
                        }
                        mageComponentCardPreviewContainer = MageComponents.CARD_PREVIEW_CONTAINER;
                        mageComponentCardPreviewPane = MageComponents.CARD_PREVIEW_PANE;
                    }
                    final Component popupContainer = MageFrame.getUI().getComponent(mageComponentCardPreviewContainer);
                    Component cardPreviewPane = MageFrame.getUI().getComponent(mageComponentCardPreviewPane);
                    if (cardPreviewPane != null) {
                        transferData.locationOnScreen = transferData.component.getLocationOnScreen();
                        Point location = new Point((int) transferData.locationOnScreen.getX() + transferData.popupOffsetX - 40, (int) transferData.locationOnScreen.getY() + transferData.popupOffsetY - 40);
                        location = GuiDisplayUtil.keepComponentInsideParent(location, parentPoint, cardPreviewPane, parentComponent);
                        location.translate(-parentPoint.x, -parentPoint.y);
                        popupContainer.setLocation(location);
                        popupContainer.setVisible(true);

                        MageCard mageCard = (MageCard) transferData.component;
                        Image image = null;
                        switch (enlargeMode) {
                            case COPY:
                                if (cardView instanceof PermanentView) {
                                    image = ImageCache.getImageOriginal(((PermanentView) cardView).getOriginal());
                                }
                                break;
                            case ALTERNATE:
                                if (cardView.getAlternateName() != null) {
                                    if (cardView instanceof PermanentView && !cardView.isFlipCard() && !cardView.canTransform() && ((PermanentView) cardView).isCopy()) {
                                        image = ImageCache.getImageOriginal(((PermanentView) cardView).getOriginal());
                                    } else {
                                        image = ImageCache.getImageOriginalAlternateName(cardView);
                                    }
                                }
                                break;
                        }
                        if (image == null) {
                            image = mageCard.getImage();
                        }
                        // shows the card in the popup Container
                        BigCard bigCard = (BigCard)cardPreviewPane;
                        displayCardInfo(mageCard, image, bigCard);


                    } else {
                        logger.warn("No Card preview Pane in Mage Frame defined. Card: " + cardView.getName());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void displayCardInfo(MageCard mageCard, Image image, BigCard bigCard) {
        if (image != null && image instanceof BufferedImage) {
            // XXX: scaled to fit width
            if (mageCard.getOriginal().isToRotate() && bigCard.getWidth() > bigCard.getHeight()) {
                image = ImageHelper.getResizedImage((BufferedImage) image, bigCard.getHeight());
                image = ImageHelper.rotate((BufferedImage) image, Math.toRadians(90));
            } else {
                image = ImageHelper.getResizedImage((BufferedImage) image, bigCard.getWidth());
            }
            bigCard.setCard(mageCard.getOriginal().getId(), enlargeMode,  image, mageCard.getOriginal().getRules());
            // if it's an ability, show only the ability text as overlay
            if (mageCard.getOriginal().isAbility() && enlargeMode.equals(EnlargeMode.NORMAL)) {
                bigCard.showTextComponent();
            } else {
                bigCard.hideTextComponent();
            }
        } else {
            JXPanel panel = GuiDisplayUtil.getDescription(mageCard.getOriginal(), bigCard.getWidth(), bigCard.getHeight());
            panel.setVisible(true);
            bigCard.hideTextComponent();
            bigCard.addJXPanel(mageCard.getOriginal().getId(), panel);
        }
    }

    private synchronized void startHideTimeout() {
        cancelTimeout();
        hideTimeout = timeoutExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                hideEnlargedCard();
            }
        }, 700, TimeUnit.MILLISECONDS);
    }

    private synchronized void cancelTimeout() {
        if (hideTimeout != null) {
            hideTimeout.cancel(false);
        }
    }
}

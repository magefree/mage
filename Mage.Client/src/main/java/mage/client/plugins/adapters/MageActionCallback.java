package mage.client.plugins.adapters;

import mage.cards.MageCard;
import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;
import mage.client.MageFrame;
import mage.client.MagePane;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.components.MageComponents;
import mage.client.dialog.PreferencesDialog;
import mage.client.game.GamePane;
import mage.client.plugins.impl.Plugins;
import mage.client.util.DefaultActionCallback;
import mage.client.util.gui.ArrowBuilder;
import mage.client.util.gui.ArrowUtil;
import mage.client.util.gui.GuiDisplayUtil;
import mage.components.CardInfoPane;
import mage.constants.EnlargeMode;
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
import java.util.List;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Class that handles the callbacks from the card panels to mage to display big
 * card images from the cards the mouse hovers on. Also handles tooltip text
 * window.
 *
 * @author Nantuko, noxx
 */
public class MageActionCallback implements ActionCallback {

    private static final Logger LOGGER = Logger.getLogger(ActionCallback.class);
    public static final int GAP_X = 5;
    public static final double COMPARE_GAP_X = 30;

    public static final int GO_DOWN_ON_DRAG_Y_OFFSET = 0;
    public static final int GO_UP_ON_DRAG_Y_OFFSET = 0;

    public static final int MIN_X_OFFSET_REQUIRED = 20;

    private Popup tooltipPopup;
    private JPopupMenu jPopupMenu;
    private BigCard bigCard;

    private CardView tooltipCard;
    private TransferData popupData;
    private JComponent cardInfoPane;
    private volatile boolean popupTextWindowOpen = false;
    private int tooltipDelay;

    enum EnlargedWindowState {

        CLOSED, NORMAL, ROTATED
    }

    private Date enlargeredViewOpened;
    private volatile EnlargedWindowState enlargedWindowState = EnlargedWindowState.CLOSED;
    //private volatile boolean enlargedImageWindowOpen = false;
    // shows the alternative card the normal card or the alternative card (copy source, other flip side, other transformed side)
    private volatile EnlargeMode enlargeMode;

    private static final ScheduledExecutorService timeoutExecutor = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> hideTimeout;

    private CardPanel prevCardPanel;
    private boolean startedDragging;
    private boolean isDragging;
    private Point initialCardPos;
    private Point initialMousePos;
    private final Set<CardPanel> cardPanels = new HashSet<>();

    public MageActionCallback() {
        enlargeMode = EnlargeMode.NORMAL;
    }

    public void setCardPreviewComponent(BigCard bigCard) {
        this.bigCard = bigCard;
    }

    public synchronized void refreshSession() {
        if (cardInfoPane == null) {
            cardInfoPane = Plugins.instance.getCardInfoPane();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e, TransferData data) {
    }

    @Override
    public void mouseEntered(MouseEvent e, final TransferData data) {
        this.popupData = data;
        handleOverNewView(data);
    }

    private void showTooltipPopup(final TransferData data, final Component parentComponent, final Point parentPoint) {
        if (data.getComponent() != null) {
            tooltipDelay = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_TOOLTIPS_DELAY, 300);
            if (tooltipDelay == 0) {
                return;
            }
        }

        if (cardInfoPane == null) {
            PopupFactory factory = PopupFactory.getSharedInstance();
            if (data.getLocationOnScreen() == null) {
                if (data.getComponent() == null) {
                    return;
                }
                data.setLocationOnScreen(data.getComponent().getLocationOnScreen());
            }
            data.getPopupText().updateText();
            tooltipPopup = factory.getPopup(data.getComponent(), data.getPopupText(), (int) data.getLocationOnScreen().getX() + data.getPopupOffsetX(), (int) data.getLocationOnScreen().getY() + data.getPopupOffsetY() + 40);
            tooltipPopup.show();
            // hack to get popup to resize to fit text
            tooltipPopup.hide();
            tooltipPopup = factory.getPopup(data.getComponent(), data.getPopupText(), (int) data.getLocationOnScreen().getX() + data.getPopupOffsetX(), (int) data.getLocationOnScreen().getY() + data.getPopupOffsetY() + 40);
            tooltipPopup.show();
        } else {
            sumbitShowPopupTask(data, parentComponent, parentPoint);
        }
    }

    private void sumbitShowPopupTask(final TransferData data, final Component parentComponent, final Point parentPoint) {
        ThreadUtils.threadPool2.submit(new Runnable() {
            @Override
            public void run() {
                ThreadUtils.sleep(tooltipDelay);

                if (tooltipCard == null
                        || !tooltipCard.equals(data.getCard())
                        || SessionHandler.getSession() == null
                        || !popupTextWindowOpen
                        || enlargedWindowState != EnlargedWindowState.CLOSED) {
                    return;
                }

                try {
                    final Component popupContainer = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
                    Component popupInfo = MageFrame.getUI().getComponent(MageComponents.CARD_INFO_PANE);
                    ((CardInfoPane) popupInfo).setCard(data.getCard(), popupContainer);
                    showPopup(popupContainer, popupInfo);
                } catch (InterruptedException e) {
                    LOGGER.error("Can't show card tooltip", e);
                    Thread.currentThread().interrupt();
                }
            }

            public void showPopup(final Component popupContainer, final Component infoPane) throws InterruptedException {
                final Component c = MageFrame.getUI().getComponent(MageComponents.DESKTOP_PANE);
                SwingUtilities.invokeLater(() -> {
                            if (!popupTextWindowOpen || enlargedWindowState != EnlargedWindowState.CLOSED) {
                                return;
                            }

                            if (data.getLocationOnScreen() == null) {
                                data.setLocationOnScreen(data.getComponent().getLocationOnScreen());
                            }

                            Point location = new Point((int) data.getLocationOnScreen().getX() + data.getPopupOffsetX() - 40, (int) data.getLocationOnScreen().getY() + data.getPopupOffsetY() - 40);
                            location = GuiDisplayUtil.keepComponentInsideParent(location, parentPoint, infoPane, parentComponent);
                            location.translate(-parentPoint.x, -parentPoint.y);
                            popupContainer.setLocation(location);
                            popupContainer.setVisible(true);
                            c.repaint();
                        }
                );
            }
        });
    }

    @Override
    public void mousePressed(MouseEvent e, TransferData data) {
        data.getComponent().requestFocusInWindow();

        // for some reason sometime mouseRelease happens before numerous Mouse_Dragged events
        // that results in not finished dragging
        clearDragging(this.prevCardPanel);

        isDragging = false;
        startedDragging = false;
        prevCardPanel = null;
        cardPanels.clear();
        Point mouse = new Point(e.getX(), e.getY());
        SwingUtilities.convertPointToScreen(mouse, data.getComponent());
        initialMousePos = new Point((int) mouse.getX(), (int) mouse.getY());
        initialCardPos = data.getComponent().getLocation();
        // Closes popup & enlarged view if a card/Permanent is selected
        hideTooltipPopup();
    }

    @Override
    public void mouseReleased(MouseEvent e, TransferData transferData) {
        CardPanel card = ((CardPanel) transferData.getComponent());
        if (e.isPopupTrigger() /*&& card.getPopupMenu() != null*/) {
            hideTooltipPopup();
        } else if (card.getZone() != null && card.getZone().equalsIgnoreCase("hand")) {
            int maxXOffset = 0;
            if (isDragging) {
                Point mouse = new Point(e.getX(), e.getY());
                SwingUtilities.convertPointToScreen(mouse, transferData.getComponent());
                maxXOffset = Math.abs((int) (mouse.getX() - initialMousePos.x));
            }

            clearDragging(card);

            this.startedDragging = false;
            if (maxXOffset < MIN_X_OFFSET_REQUIRED) { // we need this for protection from small card movements
                transferData.getComponent().requestFocusInWindow();
                DefaultActionCallback.instance.mouseClicked(transferData.getGameId(), transferData.getCard());
                // Closes popup & enlarged view if a card/Permanent is selected
                hideTooltipPopup();
            }
            e.consume();
        } else {
            transferData.getComponent().requestFocusInWindow();
            DefaultActionCallback.instance.mouseClicked(transferData.getGameId(), transferData.getCard());
            // Closes popup & enlarged view if a card/Permanent is selected
            hideTooltipPopup();
            e.consume();
        }
    }

    private void clearDragging(CardPanel card) {
        if (this.startedDragging && prevCardPanel != null && card != null) {
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
        prevCardPanel = null;
    }

    @Override
    public void mouseMoved(MouseEvent e, TransferData transferData) {
        if (!Plugins.instance.isCardPluginLoaded()) {
            return;
        }
        if (!popupData.getCard().equals(transferData.getCard())) {
            this.popupData = transferData;
            handleOverNewView(transferData);

        }
        if (bigCard == null) {
            return;
        }
        handlePopup(transferData);
    }

    @Override
    public void mouseDragged(MouseEvent e, TransferData transferData) {
        CardPanel cardPanel = ((CardPanel) transferData.getComponent());
        if (cardPanel.getZone() == null || !cardPanel.getZone().equalsIgnoreCase("hand")) {
            // drag'n'drop is allowed for HAND zone only
            return;
        }
        if (!SwingUtilities.isLeftMouseButton(e)) {
            // only allow draging with the left mouse button
            return;
        }
        isDragging = true;
        prevCardPanel = cardPanel;
        Point cardPanelLocationOld = cardPanel.getLocation();
        Point mouse = new Point(e.getX(), e.getY());
        SwingUtilities.convertPointToScreen(mouse, transferData.getComponent());
        int xOffset = cardPanel.getXOffset(cardPanel.getCardWidth());
        int newX = Math.max(initialCardPos.x + (int) (mouse.getX() - initialMousePos.x) - xOffset, 0);
        cardPanel.setCardBounds(
                newX,
                cardPanelLocationOld.y + cardPanel.getCardYOffset(),
                cardPanel.getCardWidth(),
                cardPanel.getCardHeight());
        cardPanel.getCardArea().setComponentZOrder(cardPanel, 0);
        sort(cardPanel, cardPanel.getCardArea(), false);

        if (!this.startedDragging) {
            this.startedDragging = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e, final TransferData data) {
        if (data != null) {
            hideAll(data.getGameId());
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
                    cardPanels.add((CardPanel) component);
                } else if (!startedDragging) {
                    component.setLocation(component.getLocation().x, component.getLocation().y - GO_UP_ON_DRAG_Y_OFFSET);
                }
                cards.add((CardPanel) component);
            }
        }
        sortLayout(cards, card, sortSource);
    }

    private void sortLayout(List<CardPanel> cards, CardPanel source, boolean includeSource) {
        source.getLocation().x -= COMPARE_GAP_X; // this creates nice effect

        cards.sort(Comparator.comparingInt(cp -> cp.getLocation().x));

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

    private void handleOverNewView(TransferData data) {
        // Prevent to show tooltips from panes not in front
        MagePane topPane = MageFrame.getTopMost(null);
        if (topPane instanceof GamePane) {
            if (!((GamePane) topPane).getGameId().equals(data.getGameId())) {
                return;
            }
        }

        hideTooltipPopup();
        cancelTimeout();
        Component parentComponent = SwingUtilities.getRoot(data.getComponent());
        Point parentPoint = parentComponent.getLocationOnScreen();

        if (data.getLocationOnScreen() == null) {
            data.setLocationOnScreen(data.getComponent().getLocationOnScreen());
        }

        ArrowUtil.drawArrowsForTargets(data, parentPoint);
        ArrowUtil.drawArrowsForSource(data, parentPoint);
        ArrowUtil.drawArrowsForPairedCards(data, parentPoint);
        ArrowUtil.drawArrowsForBandedCards(data, parentPoint);
        ArrowUtil.drawArrowsForEnchantPlayers(data, parentPoint);

        tooltipCard = data.getCard();
        showTooltipPopup(data, parentComponent, parentPoint);
    }

    private void handlePopup(TransferData transferData) {
        MageCard mageCard = (MageCard) transferData.getComponent();
        if (!popupTextWindowOpen
                || !Objects.equals(mageCard.getOriginal().getId(), bigCard.getCardId())) {
            if (bigCard.getWidth() > 0) {
                synchronized (MageActionCallback.class) {
                    if (!popupTextWindowOpen || !Objects.equals(mageCard.getOriginal().getId(), bigCard.getCardId())) {
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
            if (enlargedWindowState != EnlargedWindowState.CLOSED) {
                cancelTimeout();
                displayEnlargedCard(mageCard.getOriginal(), transferData);
            }
        }
    }

    @Override
    public void hideOpenComponents() {
        hideAll(null);
    }

    public void hideTooltipPopup() {
        this.tooltipCard = null;
        if (tooltipPopup != null) {
            tooltipPopup.hide();
        }
        if (jPopupMenu != null) {
            jPopupMenu.setVisible(false);
        }
        try {
            if (SessionHandler.getSession() == null) {
                return;
            }
            Component popupContainer = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
            popupContainer.setVisible(false);
        } catch (InterruptedException e) {
            LOGGER.error("Can't hide card tooltip", e);
            Thread.currentThread().interrupt();
        }
    }

    public void hideGameUpdate(UUID gameId) {
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.TARGET);
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.PAIRED);
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.BANDED);
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.SOURCE);
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.ENCHANT_PLAYERS);
    }

    public void hideAll(UUID gameId) {
        hideTooltipPopup();
        startHideTimeout();
        this.popupTextWindowOpen = false;
        if (gameId != null) {
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.TARGET);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.PAIRED);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.BANDED);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.SOURCE);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.ENCHANT_PLAYERS);
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e, TransferData transferData) {
        int notches = e.getWheelRotation();
        if (enlargedWindowState != EnlargedWindowState.CLOSED) {
            // same move direction will be ignored, opposite direction closes the enlarged window
            if (enlargeredViewOpened != null && new Date().getTime() - enlargeredViewOpened.getTime() > 1000) {
                // if the opening is back more than 1 seconds close anyway
                hideEnlargedCard();
                handleOverNewView(transferData);
            } else if (enlargeMode == EnlargeMode.NORMAL) {
                if (notches > 0) {
                    hideEnlargedCard();
                    handleOverNewView(transferData);
                }
            } else if (notches < 0) {
                hideEnlargedCard();
                handleOverNewView(transferData);
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
     * @param showAlternative defines if the original image (if it's a copied
     *                        card) or the opposite side of a transformable card will be shown
     */
    public void enlargeCard(EnlargeMode showAlternative) {
        if (enlargedWindowState == EnlargedWindowState.CLOSED) {
            this.enlargeMode = showAlternative;
            CardView cardView = null;
            if (popupData != null) {
                cardView = popupData.getCard();
            }
            if (this.popupTextWindowOpen) {
                hideTooltipPopup();
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
        enlargedWindowState = EnlargedWindowState.CLOSED;
        try {
            Component cardPreviewContainer = MageFrame.getUI().getComponent(MageComponents.CARD_PREVIEW_CONTAINER);
            if (cardPreviewContainer.isVisible()) {
                cardPreviewContainer.setVisible(false);
                cardPreviewContainer.repaint();
            }
            cardPreviewContainer = MageFrame.getUI().getComponent(MageComponents.CARD_PREVIEW_CONTAINER_ROTATED);
            if (cardPreviewContainer.isVisible()) {
                cardPreviewContainer.setVisible(false);
                cardPreviewContainer.repaint();
            }

        } catch (InterruptedException e) {
            LOGGER.warn("Can't hide enlarged card", e);
        }
    }

    private void displayEnlargedCard(final CardView cardView, final TransferData transferData) {
        ThreadUtils.threadPool3.submit(() -> {
            if (cardView == null) {
                return;
            }
            try {
                if (enlargedWindowState == EnlargedWindowState.CLOSED) {
                    return;
                }
                
                MageComponents mageComponentCardPreviewContainer;
                MageComponents mageComponentCardPreviewPane;
                if (cardView.isToRotate()) {
                    if (enlargedWindowState == EnlargedWindowState.NORMAL) {
                        hideEnlargedCard();
                        enlargedWindowState = EnlargedWindowState.ROTATED;
                    }
                    mageComponentCardPreviewContainer = MageComponents.CARD_PREVIEW_CONTAINER_ROTATED;
                    mageComponentCardPreviewPane = MageComponents.CARD_PREVIEW_PANE_ROTATED;
                } else {
                    if (enlargedWindowState == EnlargedWindowState.ROTATED) {
                        hideEnlargedCard();
                        enlargedWindowState = EnlargedWindowState.NORMAL;
                    }
                    mageComponentCardPreviewContainer = MageComponents.CARD_PREVIEW_CONTAINER;
                    mageComponentCardPreviewPane = MageComponents.CARD_PREVIEW_PANE;
                }
                final Component popupContainer = MageFrame.getUI().getComponent(mageComponentCardPreviewContainer);
                Component cardPreviewPane = MageFrame.getUI().getComponent(mageComponentCardPreviewPane);
                Component parentComponent = SwingUtilities.getRoot(transferData.getComponent());
                if (cardPreviewPane != null && parentComponent != null) {
                    Point parentPoint = parentComponent.getLocationOnScreen();
                    transferData.setLocationOnScreen(transferData.getComponent().getLocationOnScreen());
                    Point location = new Point((int) transferData.getLocationOnScreen().getX() + transferData.getPopupOffsetX() - 40, (int) transferData.getLocationOnScreen().getY() + transferData.getPopupOffsetY() - 40);
                    location = GuiDisplayUtil.keepComponentInsideParent(location, parentPoint, cardPreviewPane, parentComponent);
                    location.translate(-parentPoint.x, -parentPoint.y);
                    popupContainer.setLocation(location);
                    popupContainer.setVisible(true);
                    
                    MageCard mageCard = (MageCard) transferData.getComponent();
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
                        default:
                            break;
                    }
                    if (image == null) {
                        image = mageCard.getImage();
                    }
                    // shows the card in the popup Container
                    displayCardInfo(mageCard, image, (BigCard) cardPreviewPane);
                    
                } else {
                    LOGGER.warn("No Card preview Pane in Mage Frame defined. Card: " + cardView.getName());
                }
                
            } catch (Exception e) {
                LOGGER.warn("Problem dring display of enlarged card", e);
            }
        });
    }

    private void displayCardInfo(MageCard mageCard, Image image, BigCard bigCard) {
        if (image instanceof BufferedImage) {
            // XXX: scaled to fit width
            bigCard.setCard(mageCard.getOriginal().getId(), enlargeMode, image, mageCard.getOriginal().getRules(), mageCard.getOriginal().isToRotate());
            // if it's an ability, show only the ability text as overlay
            if (mageCard.getOriginal().isAbility() && enlargeMode == EnlargeMode.NORMAL) {
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
        enlargeredViewOpened = new Date();
    }

    private synchronized void startHideTimeout() {
        cancelTimeout();
        hideTimeout = timeoutExecutor.schedule(this::hideEnlargedCard, 700, TimeUnit.MILLISECONDS);
    }

    private synchronized void cancelTimeout() {
        if (hideTimeout != null) {
            hideTimeout.cancel(false);
        }
    }
}

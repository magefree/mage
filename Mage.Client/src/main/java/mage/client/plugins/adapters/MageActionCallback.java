package mage.client.plugins.adapters;

import mage.cards.MageCard;
import mage.cards.MageCardSpace;
import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;
import mage.client.MageFrame;
import mage.client.MagePane;
import mage.client.SessionHandler;
import mage.client.cards.BigCard;
import mage.client.cards.CardEventProducer;
import mage.client.components.MageComponents;
import mage.client.dialog.PreferencesDialog;
import mage.client.game.GamePane;
import mage.client.plugins.impl.Plugins;
import mage.client.util.ClientEventType;
import mage.client.util.DefaultActionCallback;
import mage.client.util.gui.ArrowBuilder;
import mage.client.util.gui.ArrowUtil;
import mage.client.util.gui.GuiDisplayUtil;
import mage.components.CardInfoPane;
import mage.constants.EnlargeMode;
import mage.constants.Zone;
import mage.utils.ThreadUtils;
import mage.view.CardView;
import mage.view.PermanentView;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXPanel;
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
 * window (from non-card calls, example: chats)
 * <p>
 * Only ONE action callback possible for the app
 * <p>
 * If you want to process card events in your component then use CardEventProducer, see example with mouseClicked here
 *
 * If you want virtual popup hint (without real card) then use VirtualCardInfo
 *
 * @author Nantuko, noxx, JayDi85
 */
public class MageActionCallback implements ActionCallback {

    private static final Logger logger = Logger.getLogger(ActionCallback.class);

    // hand and stack panel sizes (without scrolls)
    public static final int HAND_CARDS_BETWEEN_GAP_X = 5; // space between cards in hand // TODO: make it gui's sizeable
    public static final int STACK_CARDS_BETWEEN_GAP_X = 5; // space between cards in hand // TODO: make it gui's sizeable
    public static final MageCardSpace HAND_CARDS_MARGINS = new MageCardSpace(10, 10, 5, 5); // no needs bottom space for scrolls, it's already calced in parent panel
    public static final MageCardSpace STACK_CARDS_MARGINS = new MageCardSpace(10, 10, 5, 5);

    // effect of moving the cards apart while dragging another card above it
    // higher value -> the effect will appear earlier (depends on the card size)
    public static final int HAND_CARDS_COMPARE_GAP_X = 30;

    public static final int GO_DOWN_ON_DRAG_Y_OFFSET = 0;
    public static final int GO_UP_ON_DRAG_Y_OFFSET = 0;

    public static final int MIN_X_OFFSET_REQUIRED = 20;

    private Popup tooltipPopup;
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

    private MageCard prevCardPanel;
    private boolean startedDragging;
    private boolean isDragging; // TODO: remove drag hand code to the hand panels
    private Point initialCardPos;
    private Point initialMousePos;
    private final Set<MageCard> draggingCards = new HashSet<>();

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
    public void mouseClicked(MouseEvent e, TransferData data, boolean doubleClick) {
        // send mouse clicked event to the card's area and other cards list components for processing
        if (e.isConsumed()) {
            return;
        }
        if (data.getComponent().getCardContainer() instanceof CardEventProducer) {
            ClientEventType clickType = doubleClick ? ClientEventType.CARD_DOUBLE_CLICK : ClientEventType.CARD_CLICK;
            CardEventProducer cardContainer = (CardEventProducer) data.getComponent().getCardContainer();
            mage.client.util.Event clientEvent = new mage.client.util.Event(
                    data.getComponent().getOriginal(), clickType,
                    0, e.getX(), e.getY(), data.getComponent(), e, false
            );
            cardContainer.getCardEventSource().fireEvent(clientEvent);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e, final TransferData data) {
        // MouseEvent can be null for custom hints calls, e.g. from choose dialog
        this.popupData = data;
        handleMouseMoveOverNewCard(data);
    }

    private void startCardHintPopup(final TransferData data, final Component parentComponent, final Point parentPoint) {
        MageCard cardPanel = data.getComponent().getTopPanelRef();

        if (data.getTooltipDelay() > 0) {
            // custom tooltip
            tooltipDelay = data.getTooltipDelay();
        } else {
            // from preferences
            tooltipDelay = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_SHOW_TOOLTIPS_DELAY, 300);
        }
        if (tooltipDelay == 0) {
            // disabled
            return;
        }

        if (cardInfoPane == null) {
            // create new popup
            if (data.getLocationOnScreen() == null) {
                data.setLocationOnScreen(cardPanel.getCardLocationOnScreen().getCardPoint());
            }
            PopupFactory factory = PopupFactory.getSharedInstance();
            data.getPopupText().updateText();
            tooltipPopup = factory.getPopup(cardPanel, data.getPopupText(), (int) data.getLocationOnScreen().getX() + data.getPopupOffsetX(), (int) data.getLocationOnScreen().getY() + data.getPopupOffsetY() + 40);
            tooltipPopup.show();
            // hack to get popup to resize to fit text
            tooltipPopup.hide();
            tooltipPopup = factory.getPopup(cardPanel, data.getPopupText(), (int) data.getLocationOnScreen().getX() + data.getPopupOffsetX(), (int) data.getLocationOnScreen().getY() + data.getPopupOffsetY() + 40);
            tooltipPopup.show();
        } else {
            showCardHintPopup(data, parentComponent, parentPoint);
        }
    }

    private void showCardHintPopup(final TransferData data, final Component parentComponent, final Point parentPoint) {
        MageCard cardPanel = data.getComponent().getTopPanelRef();

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
                    logger.error("Can't show card tooltip", e);
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
                                data.setLocationOnScreen(cardPanel.getCardLocationOnScreen().getCardPoint());
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
        MageCard cardPanel = data.getComponent().getTopPanelRef();
        cardPanel.requestFocusInWindow();

        // for some reason sometime mouseRelease happens before numerous Mouse_Dragged events
        // that results in not finished dragging
        clearDragging(this.prevCardPanel);

        isDragging = false;
        startedDragging = false;
        prevCardPanel = null;
        draggingCards.clear();
        Point mouse = new Point(e.getX(), e.getY());
        SwingUtilities.convertPointToScreen(mouse, data.getComponent());
        initialMousePos = new Point((int) mouse.getX(), (int) mouse.getY());
        initialCardPos = cardPanel.getCardLocation().getCardPoint();
        // Closes popup & enlarged view if a card/Permanent is selected
        hideTooltipPopup();
    }

    @Override
    public void mouseReleased(MouseEvent e, TransferData data) {
        MageCard cardPanel = data.getComponent().getTopPanelRef();
        if (e.isPopupTrigger()) {
            hideTooltipPopup();
        } else if (cardPanel.getZone() == Zone.HAND) {
            // drag end
            int maxXOffset = 0;
            if (isDragging) {
                Point mouse = new Point(e.getX(), e.getY());
                SwingUtilities.convertPointToScreen(mouse, data.getComponent());
                maxXOffset = Math.abs((int) (mouse.getX() - initialMousePos.x));
            }

            clearDragging(cardPanel);

            this.startedDragging = false;
            if (maxXOffset < MIN_X_OFFSET_REQUIRED) { // we need this for protection from small card movements
                // default click simulation // TODO: replace to normal clicked events (change dialogs)
                cardPanel.requestFocusInWindow();
                DefaultActionCallback.instance.mouseClicked(data.getGameId(), data.getCard());
                // Closes popup & enlarged view if a card/Permanent is selected
                hideTooltipPopup();
            }
            e.consume();
        } else {
            // default click simulation
            cardPanel.requestFocusInWindow();
            DefaultActionCallback.instance.mouseClicked(data.getGameId(), data.getCard());
            // Closes popup & enlarged view if a card/Permanent is selected
            hideTooltipPopup();
            e.consume();
        }
    }

    private void clearDragging(MageCard clearCard) {
        if (this.startedDragging && prevCardPanel != null && clearCard != null) {
            // distribute cards between cards container and a drag container
            for (Component comp : clearCard.getCardContainer().getComponents()) {
                if (comp instanceof MageCard) {
                    MageCard realCard = (MageCard) comp;
                    if (draggingCards.contains(realCard)) {
                        realCard.setCardLocation(realCard.getCardLocation().getCardX(), realCard.getCardLocation().getCardY() - GO_DOWN_ON_DRAG_Y_OFFSET);
                    }
                }
            }
            clearCard.setCardLocation(clearCard.getCardLocation().getCardX(), clearCard.getCardLocation().getCardY() + GO_UP_ON_DRAG_Y_OFFSET);
            sortHandCards(clearCard, clearCard.getCardContainer(), true);
            draggingCards.clear();
        }
        prevCardPanel = null;
    }

    @Override
    public void mouseMoved(MouseEvent e, TransferData data) {
        // MouseEvent can be null for custom hints calls, e.g. from choose dialog

        if (!Plugins.instance.isCardPluginLoaded()) {
            return;
        }
        if (this.popupData == null
                || !popupData.getCard().equals(data.getCard())) {
            this.popupData = data;
            handleMouseMoveOverNewCard(data);
        }
        if (bigCard == null) {
            return;
        }
        updateCardHints(data);
    }

    @Override
    public void mouseDragged(MouseEvent e, TransferData data) {
        // start the dragging
        MageCard cardPanel = data.getComponent().getTopPanelRef();
        if (cardPanel.getZone() != Zone.HAND) {
            // drag'n'drop is allowed for HAND zone only
            return;
        }
        if (!SwingUtilities.isLeftMouseButton(e)) {
            // only allow draging with the left mouse button
            return;
        }
        isDragging = true;
        prevCardPanel = cardPanel;

        Point cardPanelLocationOld = cardPanel.getCardLocation().getCardPoint();
        Point mouse = new Point(e.getX(), e.getY());
        SwingUtilities.convertPointToScreen(mouse, data.getComponent());
        int xOffset = 0; // starting position
        int newX = Math.max(initialCardPos.x + (int) (mouse.getX() - initialMousePos.x) - xOffset, 0); // TODO: fix
        cardPanel.setCardBounds(
                newX,
                cardPanelLocationOld.y,
                cardPanel.getCardLocation().getCardWidth(),
                cardPanel.getCardLocation().getCardHeight());
        cardPanel.getCardContainer().setComponentZOrder(cardPanel, 0);
        sortHandCards(cardPanel, cardPanel.getCardContainer(), false);

        if (!this.startedDragging) {
            this.startedDragging = true;
        }
    }

    @Override
    public void mouseExited(MouseEvent e, final TransferData data) {
        // MouseEvent can be null for custom hints calls, e.g. from choose dialog
        if (data != null) {
            hideAll(data.getGameId());
        } else {
            hideAll(null);
        }
        ///clearDragging((MageCard)data.component);
    }

    @Override
    public void popupMenuCard(MouseEvent e, TransferData data) {
        // send popup menu request over card
        if (e.isConsumed()) {
            return;
        }
        e.consume();
        if (data.getComponent().getCardContainer() instanceof CardEventProducer) {
            CardEventProducer area = (CardEventProducer) data.getComponent().getCardContainer();
            mage.client.util.Event clientEvent = new mage.client.util.Event(
                    data.getComponent().getOriginal(), ClientEventType.CARD_POPUP_MENU,
                    0, e.getX(), e.getY(), data.getComponent(), e, false
            );
            area.getCardEventSource().fireEvent(clientEvent);
        }
    }

    @Override
    public void popupMenuPanel(MouseEvent e, Component sourceComponent) {
        // over non card component
        if (e.isConsumed()) {
            return;
        }
        e.consume();
        if (sourceComponent instanceof CardEventProducer) {
            CardEventProducer area = (CardEventProducer) sourceComponent;
            // card param must be empty
            mage.client.util.Event clientEvent = new mage.client.util.Event(
                    null, ClientEventType.CARD_POPUP_MENU,
                    0, e.getX(), e.getY(), e.getComponent(), e, false
            );
            area.getCardEventSource().fireEvent(clientEvent);
        }
    }

    private void sortHandCards(MageCard card, Container container, boolean sortSource) {
        java.util.List<MageCard> cards = new ArrayList<>();

        // distribute cards between cards container and a drag container
        for (Component comp : container.getComponents()) {
            if (comp instanceof MageCard) {
                MageCard realCard = (MageCard) comp;
                if (!realCard.equals(card)) {
                    if (!draggingCards.contains(realCard)) {
                        realCard.setCardLocation(realCard.getCardLocation().getCardX(), realCard.getCardLocation().getCardY() + GO_DOWN_ON_DRAG_Y_OFFSET);
                    }
                    draggingCards.add(realCard);
                } else if (!startedDragging) {
                    realCard.setCardLocation(realCard.getCardLocation().getCardX(), realCard.getCardLocation().getCardY() - GO_DOWN_ON_DRAG_Y_OFFSET);
                }
                cards.add(realCard);
            }
        }

        sortAndAnimateDraggingHandCards(cards, card, sortSource);
    }

    private void sortAndAnimateDraggingHandCards(List<MageCard> cards, MageCard source, boolean includeSource) {
        // special offset, allows to switch with first card
        source.setCardLocation(source.getCardLocation().getCardX() - HAND_CARDS_COMPARE_GAP_X, source.getCardLocation().getCardY());

        // sorting card components, so the effect above will be applied too
        cards.sort(Comparator.comparingInt(cp -> cp.getCardLocation().getCardX()));

        // WARNING, must be same sort code as Cards->layoutCards (if not then hand cards will be messed after drag)
        int dx = MageActionCallback.getHandOrStackMargins(source.getZone()).getLeft(); // starting position
        boolean createdGapForSource = false;
        for (MageCard component : cards) {
            // use real component locations, not a card's
            if (!includeSource) {
                // create special hole between cards to put dragging card into it
                if (!component.equals(source)) {
                    component.setCardLocation(dx, component.getCardLocation().getCardY());
                    dx += component.getCardLocation().getCardWidth() + MageActionCallback.getHandOrStackBetweenGapX(source.getZone());
                    // once dx is bigger than source's x position
                    // we need to create a gap for the source card
                    // but only once
                    if (!createdGapForSource && (dx + HAND_CARDS_COMPARE_GAP_X) > source.getCardLocation().getCardX()) {
                        createdGapForSource = true;
                        int gapOffset = component.getCardLocation().getCardWidth() + MageActionCallback.getHandOrStackBetweenGapX(source.getZone());
                        dx += gapOffset;
                        // workaround to apply gap on the first card (if you drag over first card)
                        if (cards.get(0).equals(source) && cards.size() > 1 && cards.get(1).equals(component)) {
                            component.setCardLocation(component.getCardLocation().getCardX() + gapOffset, component.getCardLocation().getCardY());
                        }
                    }
                }
            } else {
                component.setCardLocation(dx, component.getCardLocation().getCardY());
                dx += component.getCardLocation().getCardWidth() + MageActionCallback.getHandOrStackBetweenGapX(source.getZone());
            }
        }
    }

    private void handleMouseMoveOverNewCard(TransferData data) {
        MageCard cardPanel = data.getComponent().getTopPanelRef();

        // Prevent to show tooltips from panes not in front
        MagePane topPane = MageFrame.getTopMost(null);
        if (topPane instanceof GamePane) {
            if (data.getGameId() != null && !((GamePane) topPane).getGameId().equals(data.getGameId())) {
                return;
            }
        }

        hideTooltipPopup();
        cancelTimeout();
        Component parentComponent = SwingUtilities.getRoot(cardPanel);
        if (parentComponent == null) {
            // virtual card (example: show card popup in non cards panel like PickChoiceDialog)
            parentComponent = MageFrame.getDesktop();
        }
        Point parentPoint = parentComponent.getLocationOnScreen();

        if (data.getLocationOnScreen() == null) {
            data.setLocationOnScreen(cardPanel.getCardLocationOnScreen().getCardPoint());
        }

        ArrowUtil.drawArrowsForTargets(data, parentPoint);
        ArrowUtil.drawArrowsForSource(data, parentPoint);
        ArrowUtil.drawArrowsForPairedCards(data, parentPoint);
        ArrowUtil.drawArrowsForBandedCards(data, parentPoint);
        ArrowUtil.drawArrowsForEnchantPlayers(data, parentPoint);

        tooltipCard = data.getCard();
        startCardHintPopup(data, parentComponent, parentPoint);
    }

    private void updateCardHints(TransferData data) {
        MageCard cardPanel = data.getComponent().getTopPanelRef();

        if (!popupTextWindowOpen
                || !Objects.equals(cardPanel.getOriginal().getId(), bigCard.getCardId())) {
            if (bigCard.getWidth() > 0) {
                synchronized (MageActionCallback.class) {
                    if (!popupTextWindowOpen || !Objects.equals(cardPanel.getOriginal().getId(), bigCard.getCardId())) {
                        if (!popupTextWindowOpen) {
                            bigCard.resetCardId();
                        }
                        popupTextWindowOpen = true;
                        Image image = cardPanel.getImage();
                        displayCardInfo(cardPanel.getOriginal(), image, bigCard);
                    }
                }
            } else {
                popupTextWindowOpen = true;
            }
            if (enlargedWindowState != EnlargedWindowState.CLOSED) {
                cancelTimeout();
                displayEnlargedCard(cardPanel.getOriginal(), data);
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
        try {
            if (SessionHandler.getSession() == null) {
                return;
            }
            Component popupContainer = MageFrame.getUI().getComponent(MageComponents.POPUP_CONTAINER);
            popupContainer.setVisible(false);
        } catch (InterruptedException e) {
            logger.error("Can't hide card tooltip", e);
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
    public void mouseWheelMoved(MouseWheelEvent e, TransferData data) {
        int notches = e.getWheelRotation();
        if (enlargedWindowState != EnlargedWindowState.CLOSED) {
            // same move direction will be ignored, opposite direction closes the enlarged window
            if (enlargeredViewOpened != null && new Date().getTime() - enlargeredViewOpened.getTime() > 1000) {
                // if the opening is back more than 1 seconds close anyway
                hideEnlargedCard();
                handleMouseMoveOverNewCard(data);
            } else if (enlargeMode == EnlargeMode.NORMAL) {
                if (notches > 0) {
                    hideEnlargedCard();
                    handleMouseMoveOverNewCard(data);
                }
            } else if (notches < 0) {
                hideEnlargedCard();
                handleMouseMoveOverNewCard(data);
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
            logger.warn("Can't hide enlarged card", e);
        }
    }

    private void displayEnlargedCard(final CardView cardView, final TransferData data) {
        MageCard cardPanel = data.getComponent().getTopPanelRef();

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
                Component parentComponent = SwingUtilities.getRoot(cardPanel);
                if (cardPreviewPane != null && parentComponent != null) {
                    Point parentPoint = parentComponent.getLocationOnScreen();
                    data.setLocationOnScreen(cardPanel.getCardLocationOnScreen().getCardPoint());
                    Point location = new Point((int) data.getLocationOnScreen().getX() + data.getPopupOffsetX() - 40, (int) data.getLocationOnScreen().getY() + data.getPopupOffsetY() - 40);
                    location = GuiDisplayUtil.keepComponentInsideParent(location, parentPoint, cardPreviewPane, parentComponent);
                    location.translate(-parentPoint.x, -parentPoint.y);
                    popupContainer.setLocation(location);
                    popupContainer.setVisible(true);

                    // popup hint mode
                    Image image = cardPanel.getImage();
                    CardView displayCard = cardPanel.getOriginal();
                    switch (enlargeMode) {
                        case COPY:
                            if (cardView instanceof PermanentView) {
                                image = ImageCache.getImageOriginal(((PermanentView) cardView).getOriginal());
                            }
                            break;
                        case ALTERNATE:
                            if (cardView.getAlternateName() != null) {
                                if (cardView instanceof PermanentView
                                        && !cardView.isFlipCard()
                                        && !cardView.canTransform()
                                        && ((PermanentView) cardView).isCopy()) {
                                    image = ImageCache.getImageOriginal(((PermanentView) cardView).getOriginal());
                                } else {
                                    image = ImageCache.getImageOriginalAlternateName(cardView);
                                    displayCard = displayCard.getSecondCardFace();
                                }
                            }
                            break;
                        default:
                            break;
                    }

                    // shows the card in the popup Container
                    displayCardInfo(displayCard, image, (BigCard) cardPreviewPane);
                } else {
                    logger.warn("No Card preview Pane in Mage Frame defined. Card: " + cardView.getName());
                }

            } catch (Exception e) {
                logger.warn("Problem dring display of enlarged card", e);
            }
        });
    }

    private void displayCardInfo(CardView card, Image image, BigCard bigCard) {
        if (image instanceof BufferedImage) {
            // XXX: scaled to fit width
            bigCard.setCard(card.getId(), enlargeMode, image, card.getRules(), card.isToRotate());
            // if it's an ability, show only the ability text as overlay
            if (card.isAbility() && enlargeMode == EnlargeMode.NORMAL && isAbilityTextOverlayEnabled()) {
                bigCard.showTextComponent();
            } else {
                bigCard.hideTextComponent();
            }
        } else {
            JXPanel panel = GuiDisplayUtil.getDescription(card, bigCard.getWidth(), bigCard.getHeight());
            panel.setVisible(true);
            bigCard.hideTextComponent();
            bigCard.addJXPanel(card.getId(), panel);
        }
        enlargeredViewOpened = new Date();
    }

    private boolean isAbilityTextOverlayEnabled() {
        return PreferencesDialog.getCachedValue(PreferencesDialog.KEY_CARD_RENDERING_ABILITY_TEXT_OVERLAY, "true").equals("true");
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

    public static MageCardSpace getHandOrStackMargins(Zone zone) {
        if (zone == Zone.HAND) {
            return HAND_CARDS_MARGINS;
        } else {
            return STACK_CARDS_MARGINS;
        }
    }

    public static int getHandOrStackBetweenGapX(Zone zone) {
        if (zone == Zone.HAND) {
            return HAND_CARDS_BETWEEN_GAP_X;
        } else {
            return STACK_CARDS_BETWEEN_GAP_X;
        }
    }
}

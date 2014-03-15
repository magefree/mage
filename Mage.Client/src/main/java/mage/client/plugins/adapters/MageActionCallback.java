package mage.client.plugins.adapters;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import mage.cards.MageCard;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.components.MageComponents;
import mage.client.dialog.PreferencesDialog;
import mage.client.game.PlayAreaPanel;
import mage.client.game.PlayerPanelExt;
import mage.client.plugins.impl.Plugins;
import mage.client.util.DefaultActionCallback;
import mage.client.util.ImageHelper;
import mage.client.util.gui.ArrowBuilder;
import mage.client.util.gui.GuiDisplayUtil;
import mage.components.CardInfoPane;
import mage.constants.EnlargeMode;
import mage.remote.Session;
import mage.utils.ThreadUtils;
import mage.view.CardView;
import mage.view.PermanentView;
import mage.view.PlayerView;
import mage.view.SimpleCardsView;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXPanel;
import org.mage.plugins.card.images.ImageCache;

/**
 * Class that handles the callbacks from the card panels to mage to display big card
 * images from the cards the mouse hovers on. Also handles tooltip text window.
 *
 * @author Nantuko
 */
public class MageActionCallback implements ActionCallback {

    private static final Logger logger = Logger.getLogger(ActionCallback.class);

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
    public void mousePressed(MouseEvent e, TransferData data) {
        data.component.requestFocusInWindow();
        defaultCallback.mouseClicked(e, data.gameId, session, data.card);
    }

    @Override
    public void mouseEntered(MouseEvent e, final TransferData data) {
        hidePopup();
        cancelTimeout();

        this.popupCard = data.card;
        this.popupData = data;

        Component parentComponent = SwingUtilities.getRoot(data.component);
        Point parentPoint = parentComponent.getLocationOnScreen();

        drawArrowsForTargets(data, parentPoint);
        drawArrowsForSource(data, parentPoint);
        drawArrowsForPairedCards(data, parentPoint);
        drawArrowsForEnchantPlayers(data, parentPoint);

        showPopup(data, parentComponent, parentPoint);
    }

    private void drawArrowsForPairedCards(TransferData data, Point parentPoint) {
        if (data.card.getPairedCard() != null) {
            Point me = new Point(data.locationOnScreen);
            me.translate(-parentPoint.x, -parentPoint.y);
            UUID uuid = data.card.getPairedCard();
            for (PlayAreaPanel pa : MageFrame.getGame(data.gameId).getPlayers().values()) {
                MagePermanent permanent = pa.getBattlefieldPanel().getPermanents().get(uuid);
                if (permanent != null) {
                    Point target = permanent.getLocationOnScreen();
                    target.translate(-parentPoint.x, -parentPoint.y);
                    ArrowBuilder.getBuilder().addArrow(data.gameId, (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() + 10, Color.green, ArrowBuilder.Type.PAIRED);
                }
            }
        }
    }

    private void drawArrowsForEnchantPlayers(TransferData data, Point parentPoint) {
        if (data.gameId != null && MageFrame.getGame(data.gameId) != null) {
            for (PlayAreaPanel pa : MageFrame.getGame(data.gameId).getPlayers().values()) {
                PlayerPanelExt playAreaPanel = pa.getPlayerPanel();
                if (playAreaPanel != null && playAreaPanel.getPlayer() != null && playAreaPanel.getPlayer().hasAttachments()) {
                    Point me = new Point(data.locationOnScreen);
                    me.translate(-parentPoint.x, -parentPoint.y);
                    for (UUID attachmentId : playAreaPanel.getPlayer().getAttachments()) {
                        if (attachmentId.equals(data.card.getId())) {
                            Point player = pa.getLocationOnScreen();
                            player.translate(-parentPoint.x, -parentPoint.y);
                            ArrowBuilder.getBuilder().addArrow(data.gameId,(int) me.getX() + 35, (int) me.getY(), (int) player.getX() + 40, (int) player.getY() - 40, Color.magenta, ArrowBuilder.Type.ENCHANT_PLAYERS);
                        }
                    }
                }
            }
        }
    }

    private void drawArrowsForSource(TransferData data, Point parentPoint) {
        if (data.card.isAbility()) {
            Point me = new Point(data.locationOnScreen);
            me.translate(-parentPoint.x, -parentPoint.y);
            UUID uuid = data.card.getParentId();
            for (PlayAreaPanel pa : MageFrame.getGame(data.gameId).getPlayers().values()) {
                MagePermanent permanent = pa.getBattlefieldPanel().getPermanents().get(uuid);
                if (permanent != null) {
                    Point source = permanent.getLocationOnScreen();
                    source.translate(-parentPoint.x, -parentPoint.y);
                    ArrowBuilder.getBuilder().addArrow(data.gameId, (int) source.getX() + 40, (int) source.getY() + 10, (int) me.getX() + 35, (int) me.getY() + 20, Color.blue, ArrowBuilder.Type.SOURCE);
                }
            }
        }
    }

    private void drawArrowsForTargets(TransferData data, Point parentPoint) {
        List<UUID> targets = data.card.getTargets();
        if (targets == null) {
            return;
        }

        Point me = new Point(data.locationOnScreen);
        me.translate(-parentPoint.x, -parentPoint.y);
        for (UUID uuid : targets) {

            PlayAreaPanel p = MageFrame.getGame(data.gameId).getPlayers().get(uuid);
            if (p != null) {
                Point target = p.getLocationOnScreen();
                target.translate(-parentPoint.x, -parentPoint.y);
                ArrowBuilder.getBuilder().addArrow(data.gameId, (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() - 40, Color.red, ArrowBuilder.Type.TARGET);
                continue;
            }

            for (PlayAreaPanel panel : MageFrame.getGame(data.gameId).getPlayers().values()) {
                MagePermanent permanent = panel.getBattlefieldPanel().getPermanents().get(uuid);
                if (permanent != null) {
                    Point target = permanent.getLocationOnScreen();
                    target.translate(-parentPoint.x, -parentPoint.y);
                    ArrowBuilder.getBuilder().addArrow(data.gameId, (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() + 10, Color.red, ArrowBuilder.Type.TARGET);
                    continue;
                }

                PlayerView view = panel.getPlayerPanel().getPlayer();
                if (view != null) {
                    SimpleCardsView graveyard = view.getGraveyard();
                    if (graveyard.containsKey(uuid)) {
                        p = MageFrame.getGame(data.gameId).getPlayers().get(view.getPlayerId());
                        if (p != null) {
                            Point target = p.getLocationOnScreen();
                            target.translate(-parentPoint.x, -parentPoint.y);
                            int yOffset = p.isSmallMode() ? (PlayAreaPanel.PANEL_HEIGHT - PlayAreaPanel.PANEL_HEIGHT_SMALL) : 0;
                            ArrowBuilder.getBuilder().addArrow(data.gameId, (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 15, (int) target.getY() + 145 - yOffset, Color.red, ArrowBuilder.Type.TARGET);
                        }
                    }
                }
            }
        }
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
    public void mouseMoved(MouseEvent e, TransferData transferData) {
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

    @Override
    public void mouseExited(MouseEvent e, final TransferData data) {
        if (data != null) {
            hideAll(data.gameId);
        } else {
            hideAll(null);
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
     * Show the big card image on mouse position while hoovering over a card
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
            if (mageCard.getOriginal().isAbility()) {
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

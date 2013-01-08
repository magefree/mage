package mage.client.plugins.adapters;

import mage.Constants;
import mage.cards.MageCard;
import mage.cards.MagePermanent;
import mage.cards.action.ActionCallback;
import mage.cards.action.TransferData;
import mage.client.MageFrame;
import mage.client.cards.BigCard;
import mage.client.components.MageComponents;
import mage.client.dialog.PreferencesDialog;
import mage.client.game.PlayAreaPanel;
import mage.client.plugins.impl.Plugins;
import mage.client.util.DefaultActionCallback;
import mage.client.util.ImageHelper;
import mage.client.util.gui.ArrowBuilder;
import mage.client.util.gui.GuiDisplayUtil;
import mage.components.CardInfoPane;
import mage.remote.Session;
import mage.utils.ThreadUtils;
import mage.view.CardView;
import org.jdesktop.swingx.JXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MageActionCallback implements ActionCallback {

    private Popup popup;
    private JPopupMenu jPopupMenu;
    private BigCard bigCard;
    protected static DefaultActionCallback defaultCallback = DefaultActionCallback.getInstance();
    protected static Session session = MageFrame.getSession();
    private CardView popupCard;
    private TransferData popupData;
    private JComponent cardInfoPane;
    private volatile boolean state = false;
    private boolean enlarged = false;

    private static final ScheduledExecutorService timeoutExecutor = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> hideTimeout;

    public MageActionCallback() {
    }

    public void setCardPreviewComponent(BigCard bigCard) {
        this.bigCard = bigCard;
    }

    public void refreshSession() {
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
        if (targets != null) {
            Point me = new Point(data.locationOnScreen);
            me.translate(-parentPoint.x, -parentPoint.y);
            for (UUID uuid : targets) {

                PlayAreaPanel p = MageFrame.getGame(data.gameId).getPlayers().get(uuid);
                if (p != null) {
                    Point target = p.getLocationOnScreen();
                    target.translate(-parentPoint.x, -parentPoint.y);
                    ArrowBuilder.getBuilder().addArrow(data.gameId,(int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() - 40, Color.red, ArrowBuilder.Type.TARGET);
                } else {
                    for (PlayAreaPanel pa : MageFrame.getGame(data.gameId).getPlayers().values()) {
                        MagePermanent permanent = pa.getBattlefieldPanel().getPermanents().get(uuid);
                        if (permanent != null) {
                            Point target = permanent.getLocationOnScreen();
                            target.translate(-parentPoint.x, -parentPoint.y);
                            ArrowBuilder.getBuilder().addArrow(data.gameId, (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() + 10, Color.red, ArrowBuilder.Type.TARGET);
                        }
                    }
                }
            }
        }
    }

    private void showPopup(final TransferData data, final Component parentComponent, final Point parentPoint) {
        // skip popup for not implemented cards (they have Rarity.NA)
        if (data.card.getRarity().equals(Constants.Rarity.NA)) {
            return;
        }

        if (data.component instanceof MageCard) {
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

                if (popupCard == null || !popupCard.equals(data.card)) {
                    return;
                }

                try {
                    if (session == null || !state || enlarged) {
                        return;
                    }

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
                            if (!state || enlarged) {
                                return;
                            }
                            popupContainer.setVisible(true);
                            c.repaint();
                        }
                    }
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void mouseMoved(MouseEvent e, TransferData data) {
        if (!Plugins.getInstance().isCardPluginLoaded()) {
            return;
        }
        if (bigCard == null) {
            return;
        }

        MageCard card = (MageCard) data.component;
        if (!state || card.getOriginal().getId() != bigCard.getCardId()) {
            if (bigCard.getWidth() > 0) {
                synchronized (MageActionCallback.class) {
                    if (!state || card.getOriginal().getId() != bigCard.getCardId()) {
                        if (!state) {
                            bigCard.resetCardId();
                        }
                        state = true;
                        Image image = card.getImage();
                        displayCardInfo(card, image, bigCard);
                    }
                }
            } else {
                state = true;
            }
            displayCard(card.getOriginal(), data);
        } else {
            //hideCard();
        }
    }

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

    public void hideAll(UUID gameId) {
        hidePopup();
        startHideTimeout();
        this.state = false;
        if (gameId != null) {
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.TARGET);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.PAIRED);
            ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.SOURCE);
        }
    }

    public void enlargeCard() {
        if (!enlarged) {
            enlarged = true;
            CardView card = null;
            if (popupData != null) {
                card = popupData.card;

            }
            if (this.state) {
                hidePopup();
            }
            if (card != null) {
                displayCard(card, popupData);
            }
        }
    }

    public void hideCard() {
        if (enlarged) {
            enlarged = false;
            try {
                Component cardPreviewContainer = MageFrame.getUI().getComponent(MageComponents.CARD_PREVIEW_CONTAINER);
                cardPreviewContainer.setVisible(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e, TransferData data) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            enlargeCard();
        } else {
            hideCard();
        }
    }

    private void displayCard(final CardView card, final TransferData data) {
        if (!enlarged) {
            return;

        }

        ThreadUtils.threadPool2.submit(new Runnable() {
            @Override
            public void run() {
                if (card == null) {
                    return;
                }

                try {
                    if (!enlarged) {
                        return;
                    }

                    Component parentComponent = SwingUtilities.getRoot(data.component);
                    Point parentPoint = parentComponent.getLocationOnScreen();

                    final Component popupContainer = MageFrame.getUI().getComponent(MageComponents.CARD_PREVIEW_CONTAINER);
                    Component cardPreview = MageFrame.getUI().getComponent(MageComponents.CARD_PREVIEW_PANE);
                    //((CardInfoPaneImplExt) cardPreview).setCard(data.card);
                    Point location = new Point((int) data.locationOnScreen.getX() + data.popupOffsetX - 40, (int) data.locationOnScreen.getY() + data.popupOffsetY - 40);
                    location = GuiDisplayUtil.keepComponentInsideParent(location, parentPoint, cardPreview, parentComponent);
                    location.translate(-parentPoint.x, -parentPoint.y);
                    popupContainer.setLocation(location);
                    popupContainer.setVisible(true);

                    MageCard card = (MageCard) data.component;
                    Image image = card.getImage();
                    BigCard bigCard = (BigCard)cardPreview;
                    displayCardInfo(card, image, bigCard);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void displayCardInfo(MageCard card, Image image, BigCard bigCard) {
        if (image != null && image instanceof BufferedImage) {
            // XXX: scaled to fit width
            image = ImageHelper.getResizedImage((BufferedImage) image, bigCard.getWidth());
            bigCard.setCard(card.getOriginal().getId(), image, card.getOriginal().getRules(), card.isFoil());
            if (card.getOriginal().isAbility()) {
                bigCard.showTextComponent();
            } else {
                bigCard.hideTextComponent();
            }
        } else {
            JXPanel panel = GuiDisplayUtil.getDescription(card.getOriginal(), bigCard.getWidth(), bigCard.getHeight());
            panel.setVisible(true);
            bigCard.hideTextComponent();
            bigCard.addJXPanel(card.getOriginal().getId(), panel);
        }
    }

    private synchronized void startHideTimeout() {
        cancelTimeout();
        hideTimeout = timeoutExecutor.schedule(new Runnable() {
            @Override
            public void run() {
                hideCard();
            }
        }, 700, TimeUnit.MILLISECONDS);
    }

    private synchronized void cancelTimeout() {
        if (hideTimeout != null) {
            hideTimeout.cancel(false);
        }
    }
}


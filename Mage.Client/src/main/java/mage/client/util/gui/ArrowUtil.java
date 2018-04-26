package mage.client.util.gui;

import mage.cards.MagePermanent;
import mage.cards.action.TransferData;
import mage.client.MageFrame;
import mage.client.game.PlayAreaPanel;
import mage.client.game.PlayerPanelExt;
import mage.view.CardsView;
import mage.view.PlayerView;

import java.awt.*;
import java.util.UUID;

/**
 * @author noxx
 */
public final class ArrowUtil {

    private ArrowUtil() {}

    public static void drawArrowsForPairedCards(TransferData data, Point parentPoint) {
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

    public static void drawArrowsForBandedCards(TransferData data, Point parentPoint) {
        if (data.card.getBandedCards() != null && !data.card.getBandedCards().isEmpty()) {
            Point me = new Point(data.locationOnScreen);
            me.translate(-parentPoint.x, -parentPoint.y);
            for (PlayAreaPanel pa : MageFrame.getGame(data.gameId).getPlayers().values()) {
                for (UUID uuid : data.card.getBandedCards()) {
                    MagePermanent permanent = pa.getBattlefieldPanel().getPermanents().get(uuid);
                    if (permanent != null) {
                        Point target = permanent.getLocationOnScreen();
                        target.translate(-parentPoint.x, -parentPoint.y);
                        ArrowBuilder.getBuilder().addArrow(data.gameId, (int) me.getX() + 55, (int) me.getY() + 25, (int) target.getX() + 60, (int) target.getY() + 35, Color.yellow, ArrowBuilder.Type.BANDED);
                    }
                }
            }
        }
    }

    public static void drawArrowsForEnchantPlayers(TransferData data, Point parentPoint) {
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

    public static void drawArrowsForSource(TransferData data, Point parentPoint) {
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

    public static void drawArrowsForTargets(TransferData data, Point parentPoint) {
        java.util.List<UUID> targets = data.card.getTargets();
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
                    CardsView graveyard = view.getGraveyard();
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

}

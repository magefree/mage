package mage.client.util.gui;

import mage.cards.MageCard;
import mage.cards.action.TransferData;
import mage.client.MageFrame;
import mage.client.game.PlayAreaPanel;
import mage.client.game.PlayerPanelExt;
import mage.view.CardsView;
import mage.view.PlayerView;

import java.awt.*;
import java.util.UUID;

/**
 * @author noxx, JayDi85
 */
public final class ArrowUtil {

    private ArrowUtil() {
    }

    public static void drawArrowsForPairedCards(TransferData data, Point parentPoint) {
        if (data.getCard().getPairedCard() != null) {
            Point me = new Point(data.getLocationOnScreen());
            me.translate(-parentPoint.x, -parentPoint.y);
            UUID uuid = data.getCard().getPairedCard();
            for (PlayAreaPanel pa : MageFrame.getGamePlayers(data.getGameId()).values()) {
                MageCard permanent = pa.getBattlefieldPanel().getPermanentPanels().get(uuid);
                if (permanent != null) {
                    Point target = permanent.getCardLocationOnScreen().getCardPoint();
                    target.translate(-parentPoint.x, -parentPoint.y);
                    ArrowBuilder.getBuilder().addArrow(data.getGameId(), (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() + 10, Color.green, ArrowBuilder.Type.PAIRED);
                }
            }
        }
    }

    public static void drawArrowsForBandedCards(TransferData data, Point parentPoint) {
        if (data.getCard().getBandedCards() != null && !data.getCard().getBandedCards().isEmpty()) {
            Point me = new Point(data.getLocationOnScreen());
            me.translate(-parentPoint.x, -parentPoint.y);
            for (PlayAreaPanel pa : MageFrame.getGamePlayers(data.getGameId()).values()) {
                for (UUID uuid : data.getCard().getBandedCards()) {
                    MageCard permanent = pa.getBattlefieldPanel().getPermanentPanels().get(uuid);
                    if (permanent != null) {
                        Point target = permanent.getCardLocationOnScreen().getCardPoint();
                        target.translate(-parentPoint.x, -parentPoint.y);
                        ArrowBuilder.getBuilder().addArrow(data.getGameId(), (int) me.getX() + 55, (int) me.getY() + 25, (int) target.getX() + 60, (int) target.getY() + 35, Color.yellow, ArrowBuilder.Type.BANDED);
                    }
                }
            }
        }
    }

    public static void drawArrowsForEnchantPlayers(TransferData data, Point parentPoint) {
        if (data.getGameId() != null && MageFrame.getGame(data.getGameId()) != null) {
            for (PlayAreaPanel pa : MageFrame.getGamePlayers(data.getGameId()).values()) {
                PlayerPanelExt playAreaPanel = pa.getPlayerPanel();
                if (playAreaPanel != null && playAreaPanel.getPlayer() != null && playAreaPanel.getPlayer().hasAttachments()) {
                    Point me = new Point(data.getLocationOnScreen());
                    me.translate(-parentPoint.x, -parentPoint.y);
                    for (UUID attachmentId : playAreaPanel.getPlayer().getAttachments()) {
                        if (attachmentId.equals(data.getCard().getId())) {
                            Point player = pa.getLocationOnScreen();
                            player.translate(-parentPoint.x, -parentPoint.y);
                            ArrowBuilder.getBuilder().addArrow(data.getGameId(), (int) me.getX() + 35, (int) me.getY(), (int) player.getX() + 40, (int) player.getY() - 40, Color.magenta, ArrowBuilder.Type.ENCHANT_PLAYERS);
                        }
                    }
                }
            }
        }
    }

    public static void drawArrowsForSource(TransferData data, Point parentPoint) {
        if (data.getCard().isAbility()) {
            Point me = new Point(data.getLocationOnScreen());
            me.translate(-parentPoint.x, -parentPoint.y);
            UUID uuid = data.getCard().getParentId();
            for (PlayAreaPanel pa : MageFrame.getGamePlayers(data.getGameId()).values()) {
                MageCard permanent = pa.getBattlefieldPanel().getPermanentPanels().get(uuid);
                if (permanent != null) {
                    Point source = permanent.getCardLocationOnScreen().getCardPoint();
                    source.translate(-parentPoint.x, -parentPoint.y);
                    ArrowBuilder.getBuilder().addArrow(data.getGameId(), (int) source.getX() + 40, (int) source.getY() + 10, (int) me.getX() + 35, (int) me.getY() + 20, Color.blue, ArrowBuilder.Type.SOURCE);
                }
            }
        }
    }

    public static void drawArrowsForTargets(TransferData data, Point parentPoint) {
        java.util.List<UUID> targets = data.getCard().getTargets();
        if (targets == null) {
            return;
        }

        Point me = new Point(data.getLocationOnScreen());
        me.translate(-parentPoint.x, -parentPoint.y);
        for (UUID uuid : targets) {

            PlayAreaPanel p = MageFrame.getGamePlayers(data.getGameId()).get(uuid);
            if (p != null) {
                Point target = p.getLocationOnScreen();
                target.translate(-parentPoint.x, -parentPoint.y);
                ArrowBuilder.getBuilder().addArrow(data.getGameId(), (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() - 40, Color.red, ArrowBuilder.Type.TARGET);
                continue;
            }

            for (PlayAreaPanel panel : MageFrame.getGamePlayers(data.getGameId()).values()) {
                MageCard permanent = panel.getBattlefieldPanel().getPermanentPanels().get(uuid);
                if (permanent != null) {
                    Point target = permanent.getCardLocationOnScreen().getCardPoint();
                    target.translate(-parentPoint.x, -parentPoint.y);
                    ArrowBuilder.getBuilder().addArrow(data.getGameId(), (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 40, (int) target.getY() + 10, Color.red, ArrowBuilder.Type.TARGET);
                    continue;
                }

                PlayerView view = panel.getPlayerPanel().getPlayer();
                if (view != null) {
                    CardsView graveyard = view.getGraveyard();
                    if (graveyard.containsKey(uuid)) {
                        p = MageFrame.getGamePlayers(data.getGameId()).get(view.getPlayerId());
                        if (p != null) {
                            Point target = p.getLocationOnScreen();
                            target.translate(-parentPoint.x, -parentPoint.y);
                            int yOffset = p.isSmallMode() ? (PlayAreaPanel.PANEL_HEIGHT - PlayAreaPanel.PANEL_HEIGHT_SMALL) : 0;
                            ArrowBuilder.getBuilder().addArrow(data.getGameId(), (int) me.getX() + 35, (int) me.getY(), (int) target.getX() + 15, (int) target.getY() + 145 - yOffset, Color.red, ArrowBuilder.Type.TARGET);
                        }
                    }
                }
            }
        }
    }

}

package mage.client.combat;

import mage.cards.MageCard;
import mage.client.MageFrame;
import mage.client.game.PlayAreaPanel;
import mage.client.util.SettingsManager;
import mage.client.util.audio.AudioManager;
import mage.client.util.gui.ArrowBuilder;
import mage.view.CardView;
import mage.view.CombatGroupView;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author noxx, JayDi85
 */
public enum CombatManager {


    instance;
    private final Map<UUID, Integer> combatAttackers = new HashMap<>();
    private final Map<UUID, Integer> combatBlockers = new HashMap<>();
    private int globalBlockersCount; // we need global counter as there are several combat groups

    private static final Color ARROW_COLOR_ATTACKER = Color.red;
    private static final Color ARROW_COLOR_BLOCKED_ATTACKER = Color.gray;


    private Point parentPoint;

    public void showCombat(List<CombatGroupView> combatView, UUID gameId) {
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.COMBAT);
        displayArrows(combatView, gameId);
    }

    public void hideCombat(UUID gameId) {
        ArrowBuilder.getBuilder().removeArrowsByType(gameId, ArrowBuilder.Type.COMBAT);
        combatAttackers.remove(gameId);
        combatBlockers.remove(gameId);
    }

    private void displayArrows(List<CombatGroupView> combatView, UUID gameId) {
        parentPoint = null;
        int count = 0;
        globalBlockersCount = 0;
        for (CombatGroupView group : combatView) {
            for (CardView attacker : group.getAttackers().values()) {
                drawAttacker(group, attacker, gameId);
                count++;
            }
        }
        playAddAttackersSound(gameId, count);
        playAddBlockersSound(gameId, globalBlockersCount);
    }

    private void playAddAttackersSound(UUID gameId, int count) {
        int prevCount = 0;
        if (combatAttackers.containsKey(gameId)) {
            prevCount = combatAttackers.get(gameId);
        }
        if (prevCount < count) {
            AudioManager.playAttack();
        }
        combatAttackers.put(gameId, count);
    }

    private void drawAttacker(CombatGroupView group, CardView attacker, UUID gameId) {
        for (PlayAreaPanel pa2 : MageFrame.getGamePlayers(gameId).values()) {
            MageCard attackerCard = pa2.getBattlefieldPanel().getPermanentPanels().get(attacker.getId());
            if (attackerCard != null) {
                drawDefender(group, attackerCard, gameId);
                drawBlockers(group, attackerCard, gameId);
            }
        }
    }

    private void drawDefender(CombatGroupView group, MageCard attackerCard, UUID gameId) {
        UUID defenderId = group.getDefenderId();
        if (defenderId != null) {
            // if attacker was blocked then use another arrow color
            Color attackColor = group.getBlockers().isEmpty() ? ARROW_COLOR_ATTACKER : ARROW_COLOR_BLOCKED_ATTACKER;
            parentPoint = getParentPoint(attackerCard);
            PlayAreaPanel p = MageFrame.getGamePlayers(gameId).get(defenderId);
            if (p != null) {
                // attack to player
                Point target = p.getLocationOnScreen();
                target.translate(-parentPoint.x, -parentPoint.y);
                Point attackerPoint = attackerCard.getCardLocationOnScreen().getCardPoint();
                attackerPoint.translate(-parentPoint.x, -parentPoint.y);
                ArrowBuilder.getBuilder().addArrow(gameId, (int) attackerPoint.getX() + 45, (int) attackerPoint.getY() + 25, (int) target.getX() + 40, (int) target.getY() - 20, attackColor, ArrowBuilder.Type.COMBAT);
            } else {
                // attack to planeswalker
                for (PlayAreaPanel pa : MageFrame.getGamePlayers(gameId).values()) {
                    MageCard permanent = pa.getBattlefieldPanel().getPermanentPanels().get(defenderId);
                    if (permanent != null) {
                        Point target = permanent.getCardLocationOnScreen().getCardPoint();
                        target.translate(-parentPoint.x, -parentPoint.y);
                        Point attackerPoint = attackerCard.getCardLocationOnScreen().getCardPoint();
                        attackerPoint.translate(-parentPoint.x, -parentPoint.y);
                        ArrowBuilder.getBuilder().addArrow(gameId, (int) attackerPoint.getX() + 45, (int) attackerPoint.getY() + 25, (int) target.getX() + 40, (int) target.getY() + 10, attackColor, ArrowBuilder.Type.COMBAT);
                    }
                }
            }
        }
    }

    private void drawBlockers(CombatGroupView group, MageCard attackerCard, UUID gameId) {
        for (CardView blocker : group.getBlockers().values()) {
            for (PlayAreaPanel pa : MageFrame.getGamePlayers(gameId).values()) {
                MageCard blockerCard = pa.getBattlefieldPanel().getPermanentPanels().get(blocker.getId());
                if (blockerCard != null) {
                    parentPoint = getParentPoint(blockerCard);
                    Point blockerPoint = blockerCard.getCardLocationOnScreen().getCardPoint();
                    blockerPoint.translate(-parentPoint.x, -parentPoint.y);
                    Point attackerPoint = attackerCard.getCardLocationOnScreen().getCardPoint();
                    attackerPoint.translate(-parentPoint.x, -parentPoint.y);
                    double yRateA = (attackerCard.getCardLocation().getCardHeight() / SettingsManager.instance.getCardSize().height);
                    double xRateA = (attackerCard.getCardLocation().getCardWidth() / SettingsManager.instance.getCardSize().width);
                    double yRateB = (blockerCard.getCardLocation().getCardHeight() / SettingsManager.instance.getCardSize().height);
                    double xRateB = (blockerCard.getCardLocation().getCardWidth() / SettingsManager.instance.getCardSize().width);
                    ArrowBuilder.getBuilder().addArrow(gameId, (int) blockerPoint.getX() + (int) (55 * xRateB), (int) blockerPoint.getY() + (int) (25 * xRateB),
                            (int) attackerPoint.getX() + (int) (70 * xRateA), (int) attackerPoint.getY() + (int) (25 * yRateA), Color.blue, ArrowBuilder.Type.COMBAT);
                    globalBlockersCount++;
                }
            }
        }
    }

    private void playAddBlockersSound(UUID gameId, int count) {
        int prevCount = 0;
        if (combatBlockers.containsKey(gameId)) {
            prevCount = combatBlockers.get(gameId);
        }
        if (prevCount < count) {
            AudioManager.playBlock();
        }
        combatBlockers.put(gameId, count);
    }

    private Point getParentPoint(MageCard permanent) {
        if (parentPoint == null) {
            Component parentComponent = SwingUtilities.getRoot(permanent);
            parentPoint = parentComponent.getLocationOnScreen();
        }
        return parentPoint;
    }
}

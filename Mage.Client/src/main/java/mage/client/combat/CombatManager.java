package mage.client.combat;

import mage.cards.MagePermanent;
import mage.client.MageFrame;
import mage.client.game.PlayAreaPanel;
import mage.client.util.AudioManager;
import mage.client.util.SettingsManager;
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
 * @author noxx
 */
public class CombatManager {

    private static CombatManager combatManager;

    private Map<UUID, Integer> combatAttackers = new HashMap<UUID, Integer>();
    private Map<UUID, Integer> combatBlockers = new HashMap<UUID, Integer>();
    private int globalBlockersCount; // we need global counter as there are several combat groups

    public static CombatManager getInstance() {
        if (combatManager == null) {
            combatManager = new CombatManager();
        }
        return combatManager;
    }

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
        for (PlayAreaPanel pa2 : MageFrame.getGame(gameId).getPlayers().values()) {
            MagePermanent attackerCard = pa2.getBattlefieldPanel().getPermanents().get(attacker.getId());
            if (attackerCard != null) {
                drawDefender(group, attackerCard, gameId);
                drawBlockers(group, attackerCard, gameId);
            }
        }
    }

    private void drawDefender(CombatGroupView group, MagePermanent attackerCard, UUID gameId) {
        UUID defenderId = group.getDefenderId();
        if (defenderId != null) {
            parentPoint = getParentPoint(attackerCard);
            PlayAreaPanel p = MageFrame.getGame(gameId).getPlayers().get(defenderId);
            if (p != null) {
                Point target = p.getLocationOnScreen();
                target.translate(-parentPoint.x, -parentPoint.y);
                Point attackerPoint = attackerCard.getLocationOnScreen();
                attackerPoint.translate(-parentPoint.x, -parentPoint.y);
                ArrowBuilder.getBuilder().addArrow(gameId, (int) attackerPoint.getX() + 45, (int) attackerPoint.getY() + 25, (int) target.getX() + 40, (int) target.getY() - 20, Color.red, ArrowBuilder.Type.COMBAT);
            } else {
                for (PlayAreaPanel pa : MageFrame.getGame(gameId).getPlayers().values()) {
                    MagePermanent permanent = pa.getBattlefieldPanel().getPermanents().get(defenderId);
                    if (permanent != null) {
                        Point target = permanent.getLocationOnScreen();
                        target.translate(-parentPoint.x, -parentPoint.y);
                        Point attackerPoint = attackerCard.getLocationOnScreen();
                        attackerPoint.translate(-parentPoint.x, -parentPoint.y);
                        ArrowBuilder.getBuilder().addArrow(gameId, (int) attackerPoint.getX() + 45, (int) attackerPoint.getY() + 25, (int) target.getX() + 40, (int) target.getY() + 10, Color.red, ArrowBuilder.Type.COMBAT);
                    }
                }
            }
        }
    }

    private void drawBlockers(CombatGroupView group, MagePermanent attackerCard, UUID gameId) {
        for (CardView blocker : group.getBlockers().values()) {
            for (PlayAreaPanel pa : MageFrame.getGame(gameId).getPlayers().values()) {
                MagePermanent blockerCard = pa.getBattlefieldPanel().getPermanents().get(blocker.getId());
                if (blockerCard != null) {
                    parentPoint = getParentPoint(blockerCard);
                    Point blockerPoint = blockerCard.getLocationOnScreen();
                    blockerPoint.translate(-parentPoint.x, -parentPoint.y);
                    Point attackerPoint = attackerCard.getLocationOnScreen();
                    attackerPoint.translate(-parentPoint.x, -parentPoint.y);
                    double yRateA = (attackerCard.getSize().height / SettingsManager.getInstance().getCardSize().height);
                    double xRateA = (attackerCard.getSize().width / SettingsManager.getInstance().getCardSize().width);
                    double yRateB = (blockerCard.getSize().height / SettingsManager.getInstance().getCardSize().height);
                    double xRateB = (blockerCard.getSize().width / SettingsManager.getInstance().getCardSize().width);
                    ArrowBuilder.getBuilder().addArrow(gameId, (int) blockerPoint.getX() + (int)(55*xRateB), (int) blockerPoint.getY() + (int)(25*xRateB),
                            (int) attackerPoint.getX() + (int)(70*xRateA), (int) attackerPoint.getY() + (int)(25*yRateA), Color.blue, ArrowBuilder.Type.COMBAT);
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

    private Point getParentPoint(MagePermanent permanent) {
        if (parentPoint == null) {
            Component parentComponent = SwingUtilities.getRoot(permanent);
            parentPoint = parentComponent.getLocationOnScreen();
        }
        return parentPoint;
    }
}

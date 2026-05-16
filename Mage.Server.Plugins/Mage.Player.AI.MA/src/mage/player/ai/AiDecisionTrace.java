package mage.player.ai;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.score.GameStateEvaluator2;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Receives optional Mad AI decision trace events.
 */
public interface AiDecisionTrace extends Closeable {

    void recordCandidate(Game game, ComputerPlayer6 player, long decisionId, Ability action,
                         int depth, int actionNumber, ScoreSnapshot startScore,
                         ScoreSnapshot immediateScore, ScoreSnapshot finalScore,
                         int nodeCount, long elapsedMs, boolean searchBudgetHit,
                         int cacheHits, int cacheMisses, int cacheSize, List<BestLineStep> bestLine,
                         AiStrategyScore strategyScore, AiStateImpact immediateImpact, AiStateImpact finalImpact,
                         AiDecisionFeatureSnapshot startFeatureSnapshot,
                         AiDecisionFeatureSnapshot finalFeatureSnapshot);

    void recordChosen(Game game, ComputerPlayer6 player, long decisionId, Ability action,
                      int depth, ScoreSnapshot startScore, ScoreSnapshot finalScore,
                      int nodeCount, List<BestLineStep> bestLine, AiStrategyScore strategyScore,
                      AiStateImpact finalImpact, AiDecisionFeatureSnapshot startFeatureSnapshot,
                      AiDecisionFeatureSnapshot finalFeatureSnapshot);

    void recordSearchTimeout(Game game, ComputerPlayer6 player, int depth, int maxThinkTimeSecs,
                             int nodeCount, int rootChildren, List<BestLineStep> bestLine);

    void recordSearchNode(Game game, ComputerPlayer6 player, long decisionId, SimulationNode2 node,
                          String eventType, int searchDepth, int alpha, int beta, Integer score,
                          String note);

    void recordCombatSelection(Game game, ComputerPlayer6 player, CombatSelection selection);

    @Override
    void close();

    final class ScoreSnapshot {
        public final int total;
        public final int playerLife;
        public final int playerHand;
        public final int playerPermanents;
        public final int opponentLife;
        public final int opponentHand;
        public final int opponentPermanents;

        public ScoreSnapshot(GameStateEvaluator2.PlayerEvaluateScore score) {
            this(score.getTotalScore(), score);
        }

        public ScoreSnapshot(int total, GameStateEvaluator2.PlayerEvaluateScore score) {
            this.total = total;
            this.playerLife = score.getPlayerLifeScore();
            this.playerHand = score.getPlayerHandScore();
            this.playerPermanents = score.getPlayerPermanentsScore();
            this.opponentLife = score.getOpponentLifeScore();
            this.opponentHand = score.getOpponentHandScore();
            this.opponentPermanents = score.getOpponentPermanentsScore();
        }
    }

    final class BestLineStep {
        public final int depth;
        public final String action;

        public BestLineStep(int depth, String action) {
            this.depth = depth;
            this.action = action;
        }
    }

    static List<BestLineStep> noBestLine() {
        return Collections.emptyList();
    }

    final class CombatSelection {
        public final String combatEvent;
        public final String reason;
        public final String defenderId;
        public final String defenderName;
        public final int availableAttackers;
        public final int availableBlockers;
        public final List<Combatant> attackers;
        public final List<CombatAssignment> blockAssignments;

        public CombatSelection(String combatEvent, String reason, String defenderId, String defenderName,
                               int availableAttackers, int availableBlockers, List<Combatant> attackers,
                               List<CombatAssignment> blockAssignments) {
            this.combatEvent = combatEvent;
            this.reason = reason;
            this.defenderId = defenderId;
            this.defenderName = defenderName;
            this.availableAttackers = availableAttackers;
            this.availableBlockers = availableBlockers;
            this.attackers = immutableList(attackers);
            this.blockAssignments = immutableList(blockAssignments);
        }
    }

    final class CombatAssignment {
        public final Combatant attacker;
        public final List<Combatant> blockers;

        public CombatAssignment(Combatant attacker, List<Combatant> blockers) {
            this.attacker = attacker;
            this.blockers = immutableList(blockers);
        }
    }

    final class Combatant {
        public final String id;
        public final String name;
        public final String controllerId;
        public final String controllerName;
        public final String defenderId;
        public final String defenderName;
        public final int power;
        public final int toughness;
        public final int combatDamage;
        public final int value;
        public final List<String> abilities;

        public Combatant(String id, String name, String controllerId, String controllerName,
                         String defenderId, String defenderName, int power, int toughness,
                         int combatDamage, int value, List<String> abilities) {
            this.id = id;
            this.name = name;
            this.controllerId = controllerId;
            this.controllerName = controllerName;
            this.defenderId = defenderId;
            this.defenderName = defenderName;
            this.power = power;
            this.toughness = toughness;
            this.combatDamage = combatDamage;
            this.value = value;
            this.abilities = immutableList(abilities);
        }
    }

    static <T> List<T> immutableList(List<T> values) {
        return values == null || values.isEmpty()
                ? Collections.emptyList()
                : Collections.unmodifiableList(new ArrayList<>(values));
    }
}

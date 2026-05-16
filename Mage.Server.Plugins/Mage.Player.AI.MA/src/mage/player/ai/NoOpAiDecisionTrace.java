package mage.player.ai;

import mage.abilities.Ability;
import mage.game.Game;

import java.util.List;

public enum NoOpAiDecisionTrace implements AiDecisionTrace {
    INSTANCE;

    @Override
    public void recordCandidate(Game game, ComputerPlayer6 player, long decisionId, Ability action,
                                int depth, int actionNumber, ScoreSnapshot startScore,
                                ScoreSnapshot immediateScore, ScoreSnapshot finalScore,
                                int nodeCount, long elapsedMs, boolean searchBudgetHit,
                                int cacheHits, int cacheMisses, int cacheSize, List<BestLineStep> bestLine,
                                AiStrategyScore strategyScore, AiStateImpact immediateImpact, AiStateImpact finalImpact,
                                AiDecisionFeatureSnapshot startFeatureSnapshot,
                                AiDecisionFeatureSnapshot finalFeatureSnapshot) {
    }

    @Override
    public void recordChosen(Game game, ComputerPlayer6 player, long decisionId, Ability action,
                             int depth, ScoreSnapshot startScore, ScoreSnapshot finalScore,
                             int nodeCount, List<BestLineStep> bestLine, AiStrategyScore strategyScore,
                             AiStateImpact finalImpact, AiDecisionFeatureSnapshot startFeatureSnapshot,
                             AiDecisionFeatureSnapshot finalFeatureSnapshot) {
    }

    @Override
    public void recordSearchTimeout(Game game, ComputerPlayer6 player, int depth, int maxThinkTimeSecs,
                                    int nodeCount, int rootChildren, List<BestLineStep> bestLine) {
    }

    @Override
    public void recordSearchNode(Game game, ComputerPlayer6 player, long decisionId, SimulationNode2 node,
                                 String eventType, int searchDepth, int alpha, int beta, Integer score,
                                 String note) {
    }

    @Override
    public void recordCombatSelection(Game game, ComputerPlayer6 player, CombatSelection selection) {
    }

    @Override
    public void close() {
    }
}

package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PoliticalMemoryScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.politicalMemory.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.politicalMemory.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.politicalMemory.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public PoliticalMemoryScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "political-memory";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!AiScoreSupport.isEnabled(ENABLED_PROPERTY)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game game = context.getDecisionGame();
        Ability action = context.getAction();
        UUID playerId = context.getPlayerId();
        if (game == null || action == null || playerId == null || !AiScoreSupport.hasBadOutcome(action)
                || AiScoreSupport.countLivingOpponents(game, playerId) <= 1) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        UUID targetOpponent = AiScoreSupport.findFirstTargetedOpponent(game, action, playerId);
        if (targetOpponent == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        FfaTableSnapshot snapshot = FfaTableSnapshot.fromGame(game, playerId);
        FfaOpponentThreat targetThreat = snapshot.getOpponent(targetOpponent);
        if (targetThreat == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        FfaOpponentThreat leader = null;
        for (UUID opponentId : game.getOpponents(playerId, true)) {
            FfaOpponentThreat threat = snapshot.getOpponent(opponentId);
            if (threat != null && (leader == null || threat.getScore() > leader.getScore())) {
                leader = threat;
            }
        }
        if (leader == null || leader.getPlayerId().equals(targetOpponent)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Player target = game.getPlayer(targetOpponent);
        int gap = leader.getScore() - targetThreat.getScore();
        if (gap < 20) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        contributions.add(new AiStrategyScore.Contribution(
                "politics:avoid-kingmaking",
                -Math.min(28, gap / 2),
                "targeting lower-threat " + (target == null ? "opponent" : target.getName())
                        + " while table leader is +" + gap + " threat"
        ));

        int rawModifier = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        rawModifier = AiScoreSupport.clamp(rawModifier, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 30));
        return AiStrategyScore.of(
                context.getBaseScore(),
                rawModifier,
                AiScoreSupport.apply(rawModifier, globalApplyModifiers, APPLY_PROPERTY),
                contributions
        );
    }
}

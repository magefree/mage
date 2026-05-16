package mage.player.ai.scoring;

import mage.game.Game;
import mage.player.ai.AiStrategyScore;
import mage.players.Player;

import java.util.Collections;
import java.util.UUID;

public final class OverkillAndWinClosureScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.winClosure.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.winClosure.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.winClosure.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public OverkillAndWinClosureScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "overkill-and-win-closure";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!AiScoreSupport.isEnabled(ENABLED_PROPERTY)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game before = context.getDecisionGame();
        Game after = context.getFinalGame();
        UUID playerId = context.getPlayerId();
        if (before == null || after == null || playerId == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        UUID eliminated = findEliminatedOpponent(before, after, playerId);
        if (eliminated == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        int ownPowerSpent = Math.max(0, AiScoreSupport.totalCreaturePower(before, playerId) - AiScoreSupport.totalCreaturePower(after, playerId));
        int ownLifeSpent = Math.max(0, AiScoreSupport.playerLife(before, playerId) - AiScoreSupport.playerLife(after, playerId));
        int excessCost = ownPowerSpent + ownLifeSpent * 2;
        int raw = excessCost >= 10 ? -Math.min(30, excessCost) : 18;
        Player opponent = before.getPlayer(eliminated);
        String detail = excessCost >= 10
                ? "wins/removes " + playerName(opponent) + " but spends excess resources " + excessCost
                : "closes out " + playerName(opponent) + " with limited extra cost";
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 30));
        return AiStrategyScore.of(
                context.getBaseScore(),
                raw,
                AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY),
                Collections.singletonList(new AiStrategyScore.Contribution("win-closure:overkill", raw, detail))
        );
    }

    private static UUID findEliminatedOpponent(Game before, Game after, UUID playerId) {
        for (UUID opponentId : before.getOpponents(playerId, true)) {
            Player beforeOpponent = before.getPlayer(opponentId);
            Player afterOpponent = after.getPlayer(opponentId);
            if (beforeOpponent == null || afterOpponent == null) {
                continue;
            }
            if (beforeOpponent.isInGame() && !beforeOpponent.hasLost()
                    && (!afterOpponent.isInGame() || afterOpponent.hasLost() || afterOpponent.getLife() <= 0)) {
                return opponentId;
            }
        }
        return null;
    }

    private static String playerName(Player player) {
        return player == null ? "opponent" : player.getName();
    }
}

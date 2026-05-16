package mage.player.ai.scoring;

import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.AiStrategyScore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TokenSwarmScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.tokenSwarm.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.tokenSwarm.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.tokenSwarm.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public TokenSwarmScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "token-swarm";
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
        int beforeSwarm = largestSameNameCreatureGroup(before, playerId);
        int afterSwarm = largestSameNameCreatureGroup(after, playerId);
        int raw = 0;
        String detail = null;
        if (afterSwarm >= 5 && afterSwarm > beforeSwarm) {
            raw = Math.min(34, 10 + afterSwarm * 3);
            detail = "own wide board grows to " + afterSwarm + " matching creatures";
        } else {
            for (UUID opponentId : after.getOpponents(playerId, true)) {
                int swarm = largestSameNameCreatureGroup(after, opponentId);
                if (swarm >= 6) {
                    raw = -Math.min(32, 8 + swarm * 2);
                    detail = "opponent wide board has " + swarm + " matching creatures";
                    break;
                }
            }
        }
        if (raw == 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 35));
        return AiStrategyScore.of(
                context.getBaseScore(),
                raw,
                AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY),
                Collections.singletonList(new AiStrategyScore.Contribution("token-swarm:wide-board", raw, detail))
        );
    }

    private static int largestSameNameCreatureGroup(Game game, UUID playerId) {
        Map<String, Integer> counts = new HashMap<>();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(playerId)) {
            if (permanent != null && permanent.isCreature(game)) {
                String key = permanent.getName() + "/" + permanent.getPower().getValue() + "/" + permanent.getToughness().getValue();
                counts.put(key, counts.getOrDefault(key, 0) + 1);
            }
        }
        return counts.values().stream().mapToInt(Integer::intValue).max().orElse(0);
    }
}

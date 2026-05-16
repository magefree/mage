package mage.player.ai.scoring;

import mage.game.Game;
import mage.player.ai.AiStrategyScore;
import mage.player.ai.DeckStrategyAdvisor;

import java.util.UUID;

/**
 * Adapts the existing deck profile strategy advisor into the modular candidate scorer.
 */
public final class DeckStrategyScoreModule implements AiScoreModule {

    private final UUID playerId;
    private final int maxAppliedModifier;
    private final boolean applyModifiers;

    private transient DeckStrategyAdvisor strategyAdvisor;
    private transient UUID strategyAdvisorGameId;

    public DeckStrategyScoreModule(UUID playerId, int maxAppliedModifier, boolean applyModifiers) {
        this.playerId = playerId;
        this.maxAppliedModifier = maxAppliedModifier;
        this.applyModifiers = applyModifiers;
    }

    @Override
    public String getName() {
        return "deck-strategy";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        DeckStrategyAdvisor advisor = getStrategyAdvisor(context.getDecisionGame());
        if (advisor == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        return advisor.evaluateCandidate(
                context.getDecisionGame(),
                context.getFinalGame(),
                context.getAction(),
                context.getBaseScore()
        );
    }

    private DeckStrategyAdvisor getStrategyAdvisor(Game game) {
        if (game == null) {
            return null;
        }
        if (strategyAdvisor == null || !game.getId().equals(strategyAdvisorGameId)) {
            strategyAdvisor = DeckStrategyAdvisor.fromGame(game, playerId, maxAppliedModifier, applyModifiers);
            strategyAdvisorGameId = game.getId();
        }
        return strategyAdvisor;
    }

    @Override
    public void cleanUpOnMatchEnd() {
        strategyAdvisor = null;
        strategyAdvisorGameId = null;
    }
}

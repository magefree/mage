package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.player.ai.AiStrategyScore;
import mage.player.ai.score.AiHandCardValue;
import mage.players.Player;
import mage.target.Target;

import java.util.Collections;
import java.util.UUID;

public final class HandQualityScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.handQuality.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.handQuality.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.handQuality.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public HandQualityScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "hand-quality";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!AiScoreSupport.isEnabled(ENABLED_PROPERTY)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game decisionGame = context.getDecisionGame();
        Game finalGame = context.getFinalGame();
        Ability action = context.getAction();
        UUID playerId = context.getPlayerId();
        if (decisionGame == null || finalGame == null || action == null || playerId == null) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        int startKeepScore = AiHandCardValue.estimateRawHandKeepScore(decisionGame, playerId);
        int finalKeepScore = AiHandCardValue.estimateRawHandKeepScore(finalGame, playerId);
        int keepScoreDelta = finalKeepScore - startKeepScore;
        if (keepScoreDelta == 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        int handDelta = handSize(finalGame, playerId) - handSize(decisionGame, playerId);
        boolean discardLike = isDiscardLikeAction(decisionGame, action, playerId);
        boolean cardAccess = handDelta > 0 || AiScoreSupport.hasOutcome(action, Outcome.DrawCard);
        if (!discardLike && (!cardAccess || keepScoreDelta <= 0)) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        int raw = (int) Math.round(keepScoreDelta / 30.0d);
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 40));
        if (raw == 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        String label = keepScoreDelta < 0 ? "hand-quality:preserve-value" : "hand-quality:improve-hand";
        String detail = "hand keep score " + startKeepScore + " -> " + finalKeepScore
                + ", hand size delta " + handDelta
                + targetedHandDetail(decisionGame, action, playerId);
        return AiStrategyScore.of(
                context.getBaseScore(),
                raw,
                AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY),
                Collections.singletonList(new AiStrategyScore.Contribution(label, raw, detail))
        );
    }

    private static int handSize(Game game, UUID playerId) {
        Player player = game == null ? null : game.getPlayer(playerId);
        return player == null ? 0 : player.getHand().size();
    }

    private static boolean isDiscardLikeAction(Game game, Ability action, UUID playerId) {
        String text = AiScoreSupport.actionText(action, game);
        if (text.contains("discard")) {
            return true;
        }
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                if (isOwnHandCard(game, targetId, playerId)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static String targetedHandDetail(Game game, Ability action, UUID playerId) {
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                if (isOwnHandCard(game, targetId, playerId)) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        AiHandCardValue.CardValue value = AiHandCardValue.estimateKeepValue(game, card, playerId);
                        return ", targeted " + card.getName() + " keep=" + value.getKeepScore()
                                + (value.isDynamicStats()
                                ? " [" + value.getEstimatedPower() + "/" + value.getEstimatedToughness() + "]"
                                : "");
                    }
                }
            }
        }
        return "";
    }

    private static boolean isOwnHandCard(Game game, UUID targetId, UUID playerId) {
        if (game == null || targetId == null || playerId == null || game.getState().getZone(targetId) != Zone.HAND) {
            return false;
        }
        Card card = game.getCard(targetId);
        return card != null && playerId.equals(card.getOwnerId());
    }
}

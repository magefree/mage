package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public class MillCardsEachPlayerEffect extends OneShotEffect {

    private final DynamicValue numberCards;
    private final TargetController targetController;

    public MillCardsEachPlayerEffect(int numberCards, TargetController targetController) {
        this(StaticValue.get(numberCards), targetController);
    }

    public MillCardsEachPlayerEffect(DynamicValue numberCards, TargetController targetController) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
        this.targetController = targetController;
        this.staticText = setText();
    }

    private MillCardsEachPlayerEffect(final MillCardsEachPlayerEffect effect) {
        super(effect);
        this.numberCards = effect.numberCards;
        this.targetController = effect.targetController;
    }

    @Override
    public MillCardsEachPlayerEffect copy() {
        return new MillCardsEachPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            switch (targetController) {
                case OPPONENT:
                    for (UUID playerId : game.getOpponents(source.getControllerId())) {
                        putCardsToGravecard(playerId, source, game);
                    }
                    break;
                case ANY:
                case EACH_PLAYER:
                    for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                        putCardsToGravecard(playerId, source, game);
                    }
                    break;
                case NOT_YOU:
                    for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                        if (!playerId.equals(source.getSourceId())) {
                            putCardsToGravecard(playerId, source, game);
                        }
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("TargetController type not supported.");
            }
            return true;
        }
        return false;
    }

    private void putCardsToGravecard(UUID playerId, Ability source, Game game) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            player.millCards(numberCards.calculate(game, source, this), source, game);
        }
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case OPPONENT:
                sb.append("each opponent ");
                break;
            case ANY:
            case EACH_PLAYER:
                sb.append("each player ");
                break;
            case NOT_YOU:
                sb.append("each other player ");
                break;
            default:
                throw new UnsupportedOperationException("TargetController type not supported.");
        }
        sb.append("mills ");
        if (numberCards.toString().equals("1")) {
            sb.append("a card");
        } else {
            sb.append(CardUtil.numberToText(numberCards.toString()));
            sb.append(" cards");
        }
        return sb.toString();
    }
}

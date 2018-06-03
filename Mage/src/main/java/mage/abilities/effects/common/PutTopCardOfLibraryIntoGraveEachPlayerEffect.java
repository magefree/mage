
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class PutTopCardOfLibraryIntoGraveEachPlayerEffect extends OneShotEffect {

    private final DynamicValue numberCards;
    private final TargetController targetController;

    public PutTopCardOfLibraryIntoGraveEachPlayerEffect(int numberCards, TargetController targetController) {
        this(new StaticValue(numberCards), targetController);
    }

    public PutTopCardOfLibraryIntoGraveEachPlayerEffect(DynamicValue numberCards, TargetController targetController) {
        super(Outcome.Discard);
        this.numberCards = numberCards;
        this.targetController = targetController;
        this.staticText = setText();
    }

    public PutTopCardOfLibraryIntoGraveEachPlayerEffect(final PutTopCardOfLibraryIntoGraveEachPlayerEffect effect) {
        super(effect);
        this.numberCards = effect.numberCards;
        this.targetController = effect.targetController;
    }

    @Override
    public PutTopCardOfLibraryIntoGraveEachPlayerEffect copy() {
        return new PutTopCardOfLibraryIntoGraveEachPlayerEffect(this);
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
            player.moveCards(player.getLibrary().getTopCards(game, numberCards.calculate(game, source, this)), Zone.GRAVEYARD, source, game);
        }
    }

    private String setText() {
        StringBuilder sb = new StringBuilder();
        switch (targetController) {
            case OPPONENT:
                sb.append("each opponent ");
                break;
            case ANY:
                sb.append("each player ");
                break;
            case NOT_YOU:
                sb.append("each other player ");
                break;
            default:
                throw new UnsupportedOperationException("TargetController type not supported.");
        }
        sb.append("puts the top ");
        if(numberCards.toString().equals("1")) {
            sb.append("card");
        } else {
            sb.append(CardUtil.numberToText(numberCards.toString()));
            sb.append(" cards");
        }
        sb.append(" of their library into their graveyard");
        return sb.toString();
    }
}

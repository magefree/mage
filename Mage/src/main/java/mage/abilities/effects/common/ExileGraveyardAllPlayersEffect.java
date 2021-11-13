package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Jgod
 */
public class ExileGraveyardAllPlayersEffect extends OneShotEffect {

    private final FilterCard filter;
    private final TargetController targetController;

    public ExileGraveyardAllPlayersEffect() {
        this(StaticFilters.FILTER_CARD_CARDS);
        this.staticText = "exile all graveyards";
    }

    public ExileGraveyardAllPlayersEffect(FilterCard filter) {
        this(filter, TargetController.ANY);
    }

    public ExileGraveyardAllPlayersEffect(FilterCard filter, TargetController targetController) {
        super(Outcome.Exile);
        this.filter = filter;
        this.targetController = targetController;
        staticText = "exile all " + filter.getMessage() + " from all "
                + (targetController.equals(TargetController.OPPONENT) ? "opponents' " : "")
                + "graveyards";
    }

    public ExileGraveyardAllPlayersEffect(final ExileGraveyardAllPlayersEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.targetController = effect.targetController;
    }

    @Override
    public ExileGraveyardAllPlayersEffect copy() {
        return new ExileGraveyardAllPlayersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards toExile = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            if (TargetController.OPPONENT.equals(targetController) && playerId.equals(source.getControllerId())) {
                continue;
            }
            Player player = game.getPlayer(playerId);
            if (player != null) {
                toExile.addAll(player.getGraveyard().getCards(filter, source.getSourceId(), source.getControllerId(), game));
            }
        }
        controller.moveCards(toExile, Zone.EXILED, source, game);
        return true;
    }
}

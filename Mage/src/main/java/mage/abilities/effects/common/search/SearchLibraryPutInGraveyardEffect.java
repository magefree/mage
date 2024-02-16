package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author TheElk801
 */
public class SearchLibraryPutInGraveyardEffect extends SearchEffect {

    public SearchLibraryPutInGraveyardEffect() {
        super(new TargetCardInLibrary(StaticFilters.FILTER_CARD), Outcome.Neutral);
        staticText = "search your library for a card, put that card into your graveyard, then shuffle";
    }

    protected SearchLibraryPutInGraveyardEffect(final SearchLibraryPutInGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public SearchLibraryPutInGraveyardEffect copy() {
        return new SearchLibraryPutInGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.searchLibrary(target, source, game)) {
            controller.moveCards(game.getCard(target.getFirstTarget()), Zone.GRAVEYARD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

}
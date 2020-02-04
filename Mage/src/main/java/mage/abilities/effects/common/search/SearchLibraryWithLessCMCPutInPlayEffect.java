
package mage.abilities.effects.common.search;

import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Styxo
 */
public class SearchLibraryWithLessCMCPutInPlayEffect extends OneShotEffect {

    private final FilterCard filter;

    public SearchLibraryWithLessCMCPutInPlayEffect() {
        this(new FilterCard());
    }

    public SearchLibraryWithLessCMCPutInPlayEffect(FilterCard filter) {
        super(Outcome.PutCreatureInPlay);
        this.filter = filter;
        staticText = "Search your library for a " + filter.getMessage() + " with converted mana cost X or less, put it onto the battlefield, then shuffle your library";
    }

    public SearchLibraryWithLessCMCPutInPlayEffect(final SearchLibraryWithLessCMCPutInPlayEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCard advancedFilter = filter.copy(); // never change static objects so copy the object here before
            advancedFilter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));
            TargetCardInLibrary target = new TargetCardInLibrary(advancedFilter);
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public SearchLibraryWithLessCMCPutInPlayEffect copy() {
        return new SearchLibraryWithLessCMCPutInPlayEffect(this);
    }

}

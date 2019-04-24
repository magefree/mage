package mage.abilities.effects.common.search;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.ComparisonType;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author antoni-g
 */
public class SearchLibraryGraveyardWithLessCMCPutIntoPlay extends OneShotEffect {

    private final FilterCard filter;

    public SearchLibraryGraveyardWithLessCMCPutIntoPlay() {
        this(new FilterCard());
    }

    public SearchLibraryGraveyardWithLessCMCPutIntoPlay(FilterCard filter) {
        super(Outcome.PutCreatureInPlay);
        this.filter = filter;
        staticText = "Search your library or graveyard for a " + filter.getMessage() + " with converted mana cost X or less, put it onto the battlefield, then shuffle your library";
    }

    public SearchLibraryGraveyardWithLessCMCPutIntoPlay(final SearchLibraryGraveyardWithLessCMCPutIntoPlay effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public SearchLibraryGraveyardWithLessCMCPutIntoPlay copy() {
        return new SearchLibraryGraveyardWithLessCMCPutIntoPlay(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Card cardFound = null;
        if (controller != null  && sourceObject != null) {
            // create x cost filter
            FilterCard advancedFilter = filter.copy(); // never change static objects so copy the object here before
            advancedFilter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, source.getManaCostsToPay().getX() + 1));

            if (controller.chooseUse(outcome, "Search your library for a " + filter.getMessage() + " with CMC X or less" + '?', source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(advancedFilter);
                target.clearChosen();
                if (controller.searchLibrary(target, source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
                controller.shuffleLibrary(source, game);
            }

            if (cardFound == null && controller.chooseUse(outcome, "Search your graveyard for a " + filter.getMessage() + " with CMC X or less" + '?', source, game)) {
                TargetCard target = new TargetCard(0, 1, Zone.GRAVEYARD, advancedFilter);
                target.clearChosen();
                if (controller.choose(outcome, controller.getGraveyard(), target, game)) {
                    if (!target.getTargets().isEmpty()) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
            }

            if (cardFound != null) {
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(cardFound), game);
                controller.moveCards(cardFound, Zone.BATTLEFIELD, source, game);
            }

            return true;
        }
        return false;
    }

}

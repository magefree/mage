package mage.abilities.effects.common.search;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * @author Styxo
 */
public class SearchLibraryGraveyardPutOntoBattlefieldEffect extends OneShotEffect {

    private FilterCard filter;
    private boolean forceToSearchBoth;

    public SearchLibraryGraveyardPutOntoBattlefieldEffect(FilterCard filter) {
        this(filter, false);
    }

    public SearchLibraryGraveyardPutOntoBattlefieldEffect(FilterCard filter, boolean forceToSearchBoth) {
        this(filter, forceToSearchBoth, false);
    }

    public SearchLibraryGraveyardPutOntoBattlefieldEffect(FilterCard filter, boolean forceToSearchBoth, boolean youMay) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.forceToSearchBoth = forceToSearchBoth;
        staticText = (youMay ? "You may " : "") + "search your library and" + (forceToSearchBoth ? "" : "/or") + " graveyard for a " + filter.getMessage()
                + " and put it onto the battlefield. " + (forceToSearchBoth ? "Then shuffle" : "If you search your library this way, shuffle");
    }

    public SearchLibraryGraveyardPutOntoBattlefieldEffect(final SearchLibraryGraveyardPutOntoBattlefieldEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.forceToSearchBoth = effect.forceToSearchBoth;
    }

    @Override
    public SearchLibraryGraveyardPutOntoBattlefieldEffect copy() {
        return new SearchLibraryGraveyardPutOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Card cardFound = null;
        boolean needShuffle = false;
        if (controller != null && sourceObject != null) {
            if (forceToSearchBoth || controller.chooseUse(outcome, "Search your library for a " + filter.getMessage() + '?', source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                target.clearChosen();
                if (controller.searchLibrary(target, source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
                needShuffle = true;
            }

            if (cardFound == null && controller.chooseUse(outcome, "Search your graveyard for a " + filter.getMessage() + '?', source, game)) {
                TargetCard target = new TargetCardInYourGraveyard(0, 1, filter, true);
                target.clearChosen();
                if (controller.choose(outcome, controller.getGraveyard(), target, source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
            }

            if (cardFound != null) {
                controller.moveCards(cardFound, Zone.BATTLEFIELD, source, game);
            }

            if (needShuffle) {
                controller.shuffleLibrary(source, game);
            }

            return true;
        }

        return false;
    }
}

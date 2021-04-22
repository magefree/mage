package mage.abilities.effects.common.search;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
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
public class SearchLibraryGraveyardPutInHandEffect extends OneShotEffect {

    private FilterCard filter;
    private boolean forceToSearchBoth;

    public SearchLibraryGraveyardPutInHandEffect(FilterCard filter) {
        this(filter, false);
    }

    public SearchLibraryGraveyardPutInHandEffect(FilterCard filter, boolean forceToSearchBoth) {
        this(filter, forceToSearchBoth, false);
    }

    public SearchLibraryGraveyardPutInHandEffect(FilterCard filter, boolean forceToSearchBoth, boolean youMay) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.forceToSearchBoth = forceToSearchBoth;
        staticText = (youMay ? "you may " : "") + "search your library and" + (forceToSearchBoth ? "" : "/or") + " graveyard for a card named " + filter.getMessage()
                + ", reveal it, and put it into your hand. " + (forceToSearchBoth ? "Then shuffle" : "If you search your library this way, shuffle");
    }

    public SearchLibraryGraveyardPutInHandEffect(final SearchLibraryGraveyardPutInHandEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.forceToSearchBoth = effect.forceToSearchBoth;

    }

    @Override
    public SearchLibraryGraveyardPutInHandEffect copy() {
        return new SearchLibraryGraveyardPutInHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        Card cardFound = null;
        boolean needShuffle = false;
        if (controller != null && sourceObject != null) {
            if (forceToSearchBoth || controller.chooseUse(outcome, "Search your library for a card named " + filter.getMessage() + '?', source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                target.clearChosen();
                if (controller.searchLibrary(target, source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
                needShuffle = true;
            }

            if (cardFound == null && controller.chooseUse(outcome, "Search your graveyard for a card named " + filter.getMessage() + '?', source, game)) {
                TargetCard target = new TargetCardInYourGraveyard(0, 1, filter, true);
                target.clearChosen();
                if (controller.choose(outcome, controller.getGraveyard(), target, game)) {
                    if (!target.getTargets().isEmpty()) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
            }

            if (cardFound != null) {
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(cardFound), game);
                controller.moveCards(cardFound, Zone.HAND, source, game);
            }

            if (needShuffle) {
                controller.shuffleLibrary(source, game);
            }

            return true;
        }

        return false;
    }

}

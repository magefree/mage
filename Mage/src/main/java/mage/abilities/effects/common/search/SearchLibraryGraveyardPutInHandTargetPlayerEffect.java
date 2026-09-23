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
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCardInYourGraveyard;

public class SearchLibraryGraveyardPutInHandTargetPlayerEffect extends OneShotEffect {
    private FilterCard filter;
    private boolean forceToSearchBoth;

    public SearchLibraryGraveyardPutInHandTargetPlayerEffect(FilterCard filter) {
        this(filter, false);
    }

    public SearchLibraryGraveyardPutInHandTargetPlayerEffect(FilterCard filter, boolean forceToSearchBoth) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.forceToSearchBoth = forceToSearchBoth;
    }

    protected SearchLibraryGraveyardPutInHandTargetPlayerEffect(final SearchLibraryGraveyardPutInHandTargetPlayerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.forceToSearchBoth = effect.forceToSearchBoth;
        staticText = "Target player searches their library and" + (forceToSearchBoth ? "" : "/or") +
                " graveyard for " + (filter.getMessage().contains(" card") ? "" : "a card named ") +
                filter.getMessage() + ", reveals it, and puts it into their hand. " +
                (forceToSearchBoth ? "Then shuffle" : "If they search their library this way, they shuffle");
    }

    @Override
    public SearchLibraryGraveyardPutInHandTargetPlayerEffect copy() {
        return new SearchLibraryGraveyardPutInHandTargetPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        Card cardFound = null;
        boolean needShuffle = false;
        if (player != null && sourceObject != null) {
            if (forceToSearchBoth || player.chooseUse(outcome, "Search your library for a card named " + filter.getMessage() + "?", source, game)) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                target.clearChosen();
                if (player.searchLibrary(target, source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
                needShuffle = true;
            }

            if (cardFound == null && player.chooseUse(outcome, "Search your graveyard for a card named " + filter.getMessage() + "?", source, game)) {
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(0, 1, filter);
                target.clearChosen();
                if (player.choose(outcome, player.getGraveyard(), target, source, game)) {
                    if (!target.getTargets().isEmpty()) {
                        cardFound = game.getCard(target.getFirstTarget());
                    }
                }
            }

            if (cardFound != null) {
                player.revealCards(sourceObject.getIdName(), new CardsImpl(cardFound), game);
                player.moveCards(cardFound, Zone.HAND, source, game);
            }

            if (needShuffle) {
                player.shuffleLibrary(source, game);
            }

            return true;
        }

        return false;
    }
}

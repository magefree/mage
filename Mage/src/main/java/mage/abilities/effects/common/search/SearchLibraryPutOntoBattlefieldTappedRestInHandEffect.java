package mage.abilities.effects.common.search;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 * @author BetaSteward_at_googlemail.com, edited by Cguy7777
 */
public class SearchLibraryPutOntoBattlefieldTappedRestInHandEffect extends SearchEffect {

    private final FilterCard filter;
    private final int numToBattlefield;

    public SearchLibraryPutOntoBattlefieldTappedRestInHandEffect(TargetCardInLibrary target, int numToBattlefield) {
        super(target, Outcome.PutLandInPlay);
        staticText = "search your library for " + target.getDescription() +
                ", reveal those cards, put " + CardUtil.numberToText(numToBattlefield) + " onto the battlefield tapped and the other into your hand, then shuffle";
        this.filter = new FilterCard((numToBattlefield > 1 ? "cards" : "card") + " to put on the battlefield tapped");
        this.numToBattlefield = numToBattlefield;
    }

    public SearchLibraryPutOntoBattlefieldTappedRestInHandEffect(TargetCardInLibrary target) {
        this(target, 1);
    }

    protected SearchLibraryPutOntoBattlefieldTappedRestInHandEffect(final SearchLibraryPutOntoBattlefieldTappedRestInHandEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.numToBattlefield = effect.numToBattlefield;
    }

    @Override
    public SearchLibraryPutOntoBattlefieldTappedRestInHandEffect copy() {
        return new SearchLibraryPutOntoBattlefieldTappedRestInHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards revealed = new CardsImpl(target.getTargets());
                controller.revealCards(sourceObject.getIdName(), revealed, game);

                if (target.getTargets().size() >= 2) {
                    int maxToBattlefield = Math.min(numToBattlefield, target.getTargets().size());
                    TargetCardInLibrary targetCardsToBattlefield = new TargetCardInLibrary(maxToBattlefield, filter);
                    controller.choose(Outcome.PutLandInPlay, revealed, targetCardsToBattlefield, source, game);

                    Cards cardsToBattlefield = new CardsImpl(targetCardsToBattlefield.getTargets());
                    Cards cardsToHand = new CardsImpl(revealed);
                    if (!cardsToBattlefield.isEmpty()) {
                        controller.moveCards(cardsToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, true, false, false, null);
                        cardsToHand.removeAll(cardsToBattlefield);
                    }

                    controller.moveCardsToHandWithInfo(cardsToHand, source, game, true);
                } else if (target.getTargets().size() == 1) {
                    Cards cards = new CardsImpl(revealed);
                    Card cardToBattlefield = cards.getRandom(game);
                    if (cardToBattlefield != null) {
                        controller.moveCards(cardToBattlefield, Zone.BATTLEFIELD, source, game, true, false, false, null);
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }
}

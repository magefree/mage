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

/**
 * @author Kr4u7
 */
public class SearchLibraryPutOneInHandRestOntoBattlefieldTappedEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard("card to put on the battlefield tapped");

    public SearchLibraryPutOneInHandRestOntoBattlefieldTappedEffect(TargetCardInLibrary target) {
        super(target, Outcome.PutLandInPlay);
        staticText = "search your library for " + target.getDescription() +
                ", reveal those cards, put two onto the battlefield tapped and the other into your hand, then shuffle";
    }

    protected SearchLibraryPutOneInHandRestOntoBattlefieldTappedEffect(final SearchLibraryPutOneInHandRestOntoBattlefieldTappedEffect effect) {
        super(effect);
    }

    @Override
    public SearchLibraryPutOneInHandRestOntoBattlefieldTappedEffect copy() {
        return new SearchLibraryPutOneInHandRestOntoBattlefieldTappedEffect(this);
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
                    Cards cardsToHand = new CardsImpl(revealed);
                    while (cardsToHand.size()>1) {
                        TargetCardInLibrary targetCardToBattlefield = new TargetCardInLibrary(filter);
                        controller.choose(Outcome.PutLandInPlay, revealed, targetCardToBattlefield, source, game);

                        Card cardToBattlefield = revealed.get(targetCardToBattlefield.getFirstTarget(), game);

                        if (cardToBattlefield != null) {
                            controller.moveCards(cardToBattlefield, Zone.BATTLEFIELD, source, game, true, false, false, null);
                            cardsToHand.remove(cardToBattlefield);
                        }
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

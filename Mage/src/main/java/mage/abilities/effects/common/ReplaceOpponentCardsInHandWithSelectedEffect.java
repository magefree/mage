
package mage.abilities.effects.common;

import static java.lang.Integer.min;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author Eirkei
 */
public class ReplaceOpponentCardsInHandWithSelectedEffect extends OneShotEffect {

    public ReplaceOpponentCardsInHandWithSelectedEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent puts the cards from their hand on top of their library. Search that player's library for that many cards. The player puts those cards into their hand, then shuffles.";
    }

    protected ReplaceOpponentCardsInHandWithSelectedEffect(final ReplaceOpponentCardsInHandWithSelectedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));

        if (controller != null && targetOpponent != null) {
            int originalHandSize = targetOpponent.getHand().size();

            if (originalHandSize > 0) {
                targetOpponent.putCardsOnTopOfLibrary(targetOpponent.getHand(), game, source, false);

                int librarySize = targetOpponent.getLibrary().size();
                int searchLibraryForNum = min(originalHandSize, librarySize);

                TargetCardInLibrary target = new TargetCardInLibrary(searchLibraryForNum, searchLibraryForNum, new FilterCard());

                controller.searchLibrary(target, source, game, targetOpponent.getId());

                for (UUID cardId : target.getTargets()) {
                    Card targetCard = game.getCard(cardId);
                    targetOpponent.moveCards(targetCard, Zone.HAND, source, game);
                }

                targetOpponent.shuffleLibrary(source, game);
            }

            return true;
        }

        return false;
    }

    @Override
    public ReplaceOpponentCardsInHandWithSelectedEffect copy() {
        return new ReplaceOpponentCardsInHandWithSelectedEffect(this);
    }

}
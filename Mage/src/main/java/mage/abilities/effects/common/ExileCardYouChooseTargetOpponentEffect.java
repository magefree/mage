
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class ExileCardYouChooseTargetOpponentEffect extends OneShotEffect {

    private FilterCard filter;

    public ExileCardYouChooseTargetOpponentEffect() {
        this(new FilterCard("a card"));
    }

    public ExileCardYouChooseTargetOpponentEffect(FilterCard filter) {
        super(Outcome.Discard);
        staticText = new StringBuilder("Target opponent reveals their hand. You choose ")
                .append(filter.getMessage()).append(" from it and exile that card").toString();
        this.filter = filter;
    }

    public ExileCardYouChooseTargetOpponentEffect(final ExileCardYouChooseTargetOpponentEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        Card sourceCard = game.getCard(source.getSourceId());
        if (controller != null && opponent != null) {
            if (!opponent.getHand().isEmpty()) {
                opponent.revealCards(sourceCard != null ? sourceCard.getIdName() + " (" + sourceCard.getZoneChangeCounter(game) + ')' : "Exile", opponent.getHand(), game);
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (controller.choose(Outcome.Exile, opponent.getHand(), target, game)) {
                    Card card = opponent.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.HAND, true);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ExileCardYouChooseTargetOpponentEffect copy() {
        return new ExileCardYouChooseTargetOpponentEffect(this);
    }

}

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
 * @author LevelX2
 */
public class ExileCardYouChooseTargetOpponentEffect extends OneShotEffect {

    private final FilterCard filter;

    public ExileCardYouChooseTargetOpponentEffect(FilterCard filter) {
        super(Outcome.Discard);
        this.staticText = "target opponent reveals their hand. You choose "
                + filter.getMessage() + " from it and exile that card";
        this.filter = filter;
    }

    private ExileCardYouChooseTargetOpponentEffect(final ExileCardYouChooseTargetOpponentEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null
                || opponent.getHand().count(filter, game) < 1) {
            return false;
        }
        opponent.revealCards(source, opponent.getHand(), game);
        TargetCard target = new TargetCard(Zone.HAND, filter);
        controller.choose(Outcome.Exile, opponent.getHand(), target, game);
        Card card = opponent.getHand().get(target.getFirstTarget(), game);
        if (card != null) {
            controller.moveCards(card, Zone.EXILED, source, game);
        }
        return true;
    }

    @Override
    public ExileCardYouChooseTargetOpponentEffect copy() {
        return new ExileCardYouChooseTargetOpponentEffect(this);
    }
}

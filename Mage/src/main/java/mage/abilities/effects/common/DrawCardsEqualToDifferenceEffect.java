package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class DrawCardsEqualToDifferenceEffect extends OneShotEffect {

    private final int amount;

    public DrawCardsEqualToDifferenceEffect(int amount) {
        super(Outcome.Benefit);
        this.amount = amount;
        staticText = "draw cards equal to the difference";
    }

    private DrawCardsEqualToDifferenceEffect(final DrawCardsEqualToDifferenceEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DrawCardsEqualToDifferenceEffect copy() {
        return new DrawCardsEqualToDifferenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.drawCards(Math.max(amount - player.getHand().size(), 0), source, game) > 0;
    }
}

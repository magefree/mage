
package mage.abilities.effects.common.discard;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class DiscardHandDrawSameNumberSourceEffect extends OneShotEffect {

    public DiscardHandDrawSameNumberSourceEffect() {
        super(Outcome.DrawCard);
        staticText = "Discard all the cards in your hand, then draw that many cards";
    }

    protected DiscardHandDrawSameNumberSourceEffect(final DiscardHandDrawSameNumberSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = player.getHand().getCards(game).size();
            player.discard(amount, false, false, source, game);
            player.drawCards(amount, source, game);
            return true;
        }
        return false;
    }

    @Override
    public DiscardHandDrawSameNumberSourceEffect copy() {
        return new DiscardHandDrawSameNumberSourceEffect(this);
    }

}

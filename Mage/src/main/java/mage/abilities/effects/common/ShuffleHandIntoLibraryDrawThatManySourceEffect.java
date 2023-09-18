
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ShuffleHandIntoLibraryDrawThatManySourceEffect extends OneShotEffect {

    public ShuffleHandIntoLibraryDrawThatManySourceEffect() {
        super(Outcome.DrawCard);
        this.staticText = "shuffle the cards from your hand into your library, then draw that many cards";
    }

    protected ShuffleHandIntoLibraryDrawThatManySourceEffect(final ShuffleHandIntoLibraryDrawThatManySourceEffect effect) {
        super(effect);
    }

    @Override
    public ShuffleHandIntoLibraryDrawThatManySourceEffect copy() {
        return new ShuffleHandIntoLibraryDrawThatManySourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int cardsHand = controller.getHand().size();
            if (cardsHand > 0) {
                controller.moveCards(controller.getHand(), Zone.LIBRARY, source, game);
                controller.shuffleLibrary(source, game);
                game.getState().processAction(game); // then
                controller.drawCards(cardsHand, source, game);
            }
            return true;
        }
        return false;
    }
}

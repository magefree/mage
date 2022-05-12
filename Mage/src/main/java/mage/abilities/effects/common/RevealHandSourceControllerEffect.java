
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author noahg
 */
public class RevealHandSourceControllerEffect extends OneShotEffect {

    public RevealHandSourceControllerEffect() {
        super(Outcome.Discard);
        this.staticText = "reveal your hand";
    }

    public RevealHandSourceControllerEffect(final RevealHandSourceControllerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            player.revealCards(sourceObject.getIdName(), player.getHand(), game);
            return true;
        }
        return false;
    }

    @Override
    public RevealHandSourceControllerEffect copy() {
        return new RevealHandSourceControllerEffect(this);
    }
}


package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class BecomesMonarchSourceEffect extends OneShotEffect {

    public BecomesMonarchSourceEffect() {
        super(Outcome.Benefit);
        staticText = "you become the monarch";
    }

    protected BecomesMonarchSourceEffect(final BecomesMonarchSourceEffect effect) {
        super(effect);
    }

    @Override
    public BecomesMonarchSourceEffect copy() {
        return new BecomesMonarchSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            game.setMonarchId(source, source.getControllerId());
            return true;
        }
        return false;
    }

}

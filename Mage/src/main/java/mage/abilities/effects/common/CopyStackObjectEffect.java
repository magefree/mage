package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class CopyStackObjectEffect extends OneShotEffect {

    public CopyStackObjectEffect() {
        super(Outcome.Copy);
        staticText = "copy that ability. You may choose new targets for the copy";
    }

    private CopyStackObjectEffect(final CopyStackObjectEffect effect) {
        super(effect);
    }

    @Override
    public CopyStackObjectEffect copy() {
        return new CopyStackObjectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        StackObject ability = (StackObject) getValue("stackObject");
        if (controller == null || ability == null) {
            return false;
        }
        ability.createCopyOnStack(game, source, source.getControllerId(), true);
        return true;
    }
}

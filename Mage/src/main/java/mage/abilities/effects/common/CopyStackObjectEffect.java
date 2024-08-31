package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class CopyStackObjectEffect extends OneShotEffect {

    public CopyStackObjectEffect() {
        this("that ability");
    }

    public CopyStackObjectEffect(String name) {
        super(Outcome.Copy);
        staticText = "copy "+ name + ". You may choose new targets for the copy";
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
        UUID id = getTargetPointer().getFirst(game, source);
        StackObject object = game.getStack().getStackObject(id);
        if (object == null) {
            object = (StackObject) game.getLastKnownInformation(id, Zone.STACK);
        }
        if (controller == null || object == null) {
            return false;
        }
        object.createCopyOnStack(game, source, source.getControllerId(), true);
        return true;
    }
}

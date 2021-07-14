package mage.abilities.effects.common.turn;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;

import java.util.UUID;

/**
 * @author nantuko
 */
public class ControlTargetPlayerNextTurnEffect extends OneShotEffect {

    public ControlTargetPlayerNextTurnEffect() {
        super(Outcome.Benefit);
        staticText = "You control target player during that player's next turn. <i>(You see all cards that player could see and make all decisions for the player.)</i>";
    }

    public ControlTargetPlayerNextTurnEffect(ControlTargetPlayerNextTurnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        UUID controllerId = source.getControllerId();
        if (targetId != null && controllerId != null && !targetId.equals(controllerId)) {
            game.getState().getTurnMods().add(new TurnMod(targetId, controllerId));
            return true;
        }
        return false;
    }

    @Override
    public ControlTargetPlayerNextTurnEffect copy() {
        return new ControlTargetPlayerNextTurnEffect(this);
    }
}

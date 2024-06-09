package mage.abilities.effects.common.turn;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;

import java.util.Objects;
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
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null) {
            return false;
        }

        if (!Objects.equals(source.getControllerId(), targetPlayer.getId())) {
            game.getState().getTurnMods().add(new TurnMod(targetPlayer.getId()).withNewController(source.getControllerId()));
            return true;
        }

        return false;
    }

    @Override
    public ControlTargetPlayerNextTurnEffect copy() {
        return new ControlTargetPlayerNextTurnEffect(this);
    }
}

package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author xenohedron
 */
public class PlayerToRightGainsControlOfSourceEffect extends OneShotEffect {

    public PlayerToRightGainsControlOfSourceEffect() {
        super(Outcome.Detriment);
        this.staticText = "the player to your right gains control of {this}";
    }

    protected PlayerToRightGainsControlOfSourceEffect(final PlayerToRightGainsControlOfSourceEffect effect) {
        super(effect);
    }

    @Override
    public PlayerToRightGainsControlOfSourceEffect copy() {
        return new PlayerToRightGainsControlOfSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return true;
        }
        UUID playerToRightId = game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .reduce((u1, u2) -> u2)
                .orElse(null);
        if (playerToRightId == null) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, playerToRightId
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}

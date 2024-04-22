

package mage.abilities.effects.common.continuous;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class CantGainLifeTargetEffect extends ContinuousEffectImpl {

    public CantGainLifeTargetEffect(Duration duration) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        StringBuilder sb = new StringBuilder("If that player would gain life");
        if (!this.duration.toString().isEmpty()) {
            if (duration == Duration.EndOfTurn) {
                sb.append("this turn, ");
            } else {
                sb.append(' ').append(duration.toString());
            }
        }
        sb.append("that player gains no life instead");
        staticText = sb.toString();
    }

    protected CantGainLifeTargetEffect(final CantGainLifeTargetEffect effect) {
        super(effect);
    }

    @Override
    public CantGainLifeTargetEffect copy() {
        return new CantGainLifeTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : this.getTargetPointer().getTargets(game, source)) {
            Player targetPlayer = game.getPlayer(playerId);
            if (targetPlayer != null) {
                targetPlayer.setCanGainLife(false);
            }
        }
        return true;
    }

}

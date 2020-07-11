
package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */

public class CanAttackAsThoughItDidntHaveDefenderTargetEffect extends AsThoughEffectImpl {

    public CanAttackAsThoughItDidntHaveDefenderTargetEffect(Duration duration) {
        super(AsThoughEffectType.ATTACK, duration, Outcome.Benefit);
    }

    public CanAttackAsThoughItDidntHaveDefenderTargetEffect(final CanAttackAsThoughItDidntHaveDefenderTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CanAttackAsThoughItDidntHaveDefenderTargetEffect copy() {
        return new CanAttackAsThoughItDidntHaveDefenderTargetEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return this.getTargetPointer().getTargets(game, source).contains(objectId);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        if (!mode.getTargets().isEmpty()) {
            if (this.duration == Duration.EndOfTurn) {
                return "target " + mode.getTargets().get(0).getTargetName() + " can attack this turn as though it didn't have defender";
            } else {
                return "target " + mode.getTargets().get(0).getTargetName() + " can attack as though it didn't have defender";
            }
        } else {
            throw new UnsupportedOperationException("No target defined");
        }
    }
}

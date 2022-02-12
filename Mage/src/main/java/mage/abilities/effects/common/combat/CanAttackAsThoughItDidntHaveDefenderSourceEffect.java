
package mage.abilities.effects.common.combat;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class CanAttackAsThoughItDidntHaveDefenderSourceEffect extends AsThoughEffectImpl {

    public CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration duration) {
        this(duration, "{this}");
    }

    public CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration duration, String description) {
        super(AsThoughEffectType.ATTACK, duration, Outcome.Benefit);
        staticText = description + " can attack "
                + (duration == Duration.EndOfTurn ? "this turn " : "")
                + "as though it didn't have defender";
    }

    public CanAttackAsThoughItDidntHaveDefenderSourceEffect(final CanAttackAsThoughItDidntHaveDefenderSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CanAttackAsThoughItDidntHaveDefenderSourceEffect copy() {
        return new CanAttackAsThoughItDidntHaveDefenderSourceEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return objectId.equals(source.getSourceId());
    }

}

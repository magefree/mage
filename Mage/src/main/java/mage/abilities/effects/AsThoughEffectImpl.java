package mage.abilities.effects;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Outcome;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class AsThoughEffectImpl extends ContinuousEffectImpl implements AsThoughEffect {

    protected AsThoughEffectType type;

    public AsThoughEffectImpl(AsThoughEffectType type, Duration duration, Outcome outcome) {
        super(duration, outcome);
        this.type = type;
        this.effectType = EffectType.ASTHOUGH;
    }

    public AsThoughEffectImpl(final AsThoughEffectImpl effect) {
        super(effect);
        this.type = effect.type;
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (getAsThoughEffectType().equals(AsThoughEffectType.LOOK_AT_FACE_DOWN)) {
            return applies(objectId, source, playerId, game);
        } else {
            return applies(objectId, source, affectedAbility.getControllerId(), game);
        }
    }

    @Override
    public AsThoughEffectType getAsThoughEffectType() {
        return type;
    }

}

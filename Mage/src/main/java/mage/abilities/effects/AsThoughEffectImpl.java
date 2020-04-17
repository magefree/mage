package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
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
        // affectedControllerId = player to check
        if (getAsThoughEffectType().equals(AsThoughEffectType.LOOK_AT_FACE_DOWN)) {
            return applies(objectId, source, playerId, game);
        } else {
            return applies(objectId, source, playerId, game);
        }
    }

    @Override
    public AsThoughEffectType getAsThoughEffectType() {
        return type;
    }

    /**
     * Helper to check that affectedAbility is compatible for alternative cast modifications by setCastSourceIdWithAlternateMana
     */
    public boolean isAbilityAppliedForAlternateCast(Card cardToCheck, Ability affectedAbilityToCheck, UUID playerToCheck, Ability source) {
        return cardToCheck != null
                && playerToCheck.equals(source.getControllerId())
                && cardToCheck.isOwnedBy(source.getControllerId())
                && affectedAbilityToCheck instanceof ActivatedAbility
                && (affectedAbilityToCheck.getAbilityType() == AbilityType.SPELL
                || affectedAbilityToCheck.getAbilityType() == AbilityType.PLAY_LAND);
    }
}

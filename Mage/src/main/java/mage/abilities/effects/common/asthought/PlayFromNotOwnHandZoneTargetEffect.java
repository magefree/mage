package mage.abilities.effects.common.asthought;

import mage.abilities.Ability;
import mage.abilities.PlayLandAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class PlayFromNotOwnHandZoneTargetEffect extends AsThoughEffectImpl {

    private final Zone fromZone;
    private final TargetController allowedCaster;
    private final boolean withoutMana;
    private final boolean onlyCastAllowed; // can cast spells, but can't play lands

    public PlayFromNotOwnHandZoneTargetEffect() {
        this(Duration.EndOfTurn);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Duration duration) {
        this(Zone.ALL, TargetController.YOU, duration);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Zone fromZone, Duration duration) {
        this(fromZone, TargetController.YOU, duration);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Zone fromZone, TargetController allowedCaster, Duration duration) {
        this(fromZone, allowedCaster, duration, false);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Zone fromZone, TargetController allowedCaster, Duration duration, boolean withoutMana) {
        this(fromZone, allowedCaster, duration, withoutMana, false);
    }

    public PlayFromNotOwnHandZoneTargetEffect(Zone fromZone, TargetController allowedCaster, Duration duration, boolean withoutMana, boolean onlyCastAllowed) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, withoutMana ? Outcome.PlayForFree : Outcome.PutCardInPlay);
        this.fromZone = fromZone;
        this.allowedCaster = allowedCaster;
        this.withoutMana = withoutMana;
        this.onlyCastAllowed = onlyCastAllowed;
    }

    public PlayFromNotOwnHandZoneTargetEffect(final PlayFromNotOwnHandZoneTargetEffect effect) {
        super(effect);
        this.fromZone = effect.fromZone;
        this.allowedCaster = effect.allowedCaster;
        this.withoutMana = effect.withoutMana;
        this.onlyCastAllowed = effect.onlyCastAllowed;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public PlayFromNotOwnHandZoneTargetEffect copy() {
        return new PlayFromNotOwnHandZoneTargetEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return applies(objectId, null, source, game, affectedControllerId);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        if (affectedAbility == null) {
            // ContinuousEffects.asThough already checks affectedAbility, so that error must never be called here
            // PLAY_FROM_NOT_OWN_HAND_ZONE must applies to affectedAbility only
            // If you see it then parent conditional effect must override both applies methods to support different
            // AsThough effect types in one conditional effect
            throw new IllegalArgumentException("ERROR, can't call applies method on empty affectedAbility");
        }

        // invalid targets
        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.isEmpty()) {
            this.discard();
            return false;
        }

        // invalid zone
        if (!game.getState().getZone(objectId).match(fromZone)) {
            return false;
        }

        // invalid caster
        switch (allowedCaster) {
            case YOU:
                if (playerId != source.getControllerId()) {
                    return false;
                }
                break;
            case OPPONENT:
                if (!game.getOpponents(source.getControllerId()).contains(playerId)) {
                    return false;
                }
                break;
            case OWNER:
                if (playerId != game.getCard(objectId).getOwnerId()) {
                    return false;
                }
                break;
            case ANY:
                break;
        }

        // targets goes to main card all the time
        UUID objectIdToCast = CardUtil.getMainCardId(game, objectId);
        if (!targets.contains(objectIdToCast)) {
            return false;
        }

        // if can't play lands
        if (!affectedAbility.getAbilityType().isPlayCardAbility()
                || onlyCastAllowed && affectedAbility instanceof PlayLandAbility) {
            return false;
        }

        // OK, allow to play
        if (withoutMana) {
            allowCardToPlayWithoutMana(objectId, source, playerId, game);
        }
        return true;
    }
}

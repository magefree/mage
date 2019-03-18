package mage.abilities.effects.common.asthought;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class PlayFromNotOwnHandZoneTargetEffect extends AsThoughEffectImpl {

    private final Zone fromZone;
    private final TargetController allowedCaster;

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
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, duration, Outcome.Benefit);
        this.fromZone = fromZone;
        this.allowedCaster = allowedCaster;
    }

    public PlayFromNotOwnHandZoneTargetEffect(final PlayFromNotOwnHandZoneTargetEffect effect) {
        super(effect);
        this.fromZone = effect.fromZone;
        this.allowedCaster = effect.allowedCaster;
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
        switch (allowedCaster) {
            case YOU:
                if (affectedControllerId != source.getControllerId()) {
                    return false;
                }
                break;
            case OPPONENT:
                if (!game.getOpponents(source.getControllerId()).contains(affectedControllerId)) {
                    return false;
                }
                break;
            case ANY:
                break;
        }
        List<UUID> targets = getTargetPointer().getTargets(game, source);
        if (targets.isEmpty()) {
            this.discard();
            return false;
        }
        return targets.contains(objectId)
                && affectedControllerId.equals(source.getControllerId())
                && game.getState().getZone(objectId).match(fromZone);
    }
}

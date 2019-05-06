package mage.abilities.effects.common.continuous;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GainControlTargetEffect extends ContinuousEffectImpl {

    protected UUID controllingPlayerId;
    private boolean fixedControl;

    public GainControlTargetEffect(Duration duration) {
        this(duration, false, null);
    }

    /**
     *
     * @param duration
     * @param fixedControl Controlling player is fixed even if the controller of
     * the ability changes later
     */
    public GainControlTargetEffect(Duration duration, boolean fixedControl) {
        this(duration, fixedControl, null);
    }

    /**
     *
     * @param duration
     * @param controllingPlayerId Player that controls the target creature
     */
    public GainControlTargetEffect(Duration duration, UUID controllingPlayerId) {
        this(duration, true, controllingPlayerId);

    }

    public GainControlTargetEffect(Duration duration, boolean fixedControl, UUID controllingPlayerId) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllingPlayerId = controllingPlayerId;
        this.fixedControl = fixedControl;
    }

    public GainControlTargetEffect(final GainControlTargetEffect effect) {
        super(effect);
        this.controllingPlayerId = effect.controllingPlayerId;
        this.fixedControl = effect.fixedControl;
    }

    @Override
    public GainControlTargetEffect copy() {
        return new GainControlTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        if (this.controllingPlayerId == null && fixedControl) {
            this.controllingPlayerId = source.getControllerId();
        }
        super.init(source, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean targetStillExists = false;
            for (UUID permanentId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    targetStillExists = true;
                    if (!permanent.isControlledBy(controllingPlayerId)) {
                        GameEvent loseControlEvent = GameEvent.getEvent(GameEvent.EventType.LOSE_CONTROL, permanentId, source.getId(), permanent.getControllerId());
                        if (game.replaceEvent(loseControlEvent)) {
                            return false;
                        }
                        if (controllingPlayerId != null) {
                            permanent.changeControllerId(controllingPlayerId, game);
                            permanent.getAbilities().setControllerId(controllingPlayerId);
                        } else {
                            permanent.changeControllerId(source.getControllerId(), game);
                            permanent.getAbilities().setControllerId(source.getControllerId());
                        }
                    }
                }
            }
            // no valid target exists and the controller is no longer in the game, effect can be discarded
            if (!targetStillExists
                    || !controller.isInGame()) {
                discard();
            }
            return true;
        }
        discard(); // controller no longer exists
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }

        if (mode.getTargets().isEmpty()) {
            return "gain control of target permanent";
        }

        Target target = mode.getTargets().get(0);
        StringBuilder sb = new StringBuilder("gain control of ");
        if (target.getMaxNumberOfTargets() > 1) {
            if (target.getNumberOfTargets() < target.getMaxNumberOfTargets()) {
                sb.append("up to ");
            }
            sb.append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ");
        } else if (!target.getTargetName().startsWith("another")) {
            sb.append("target ");
        }
        sb.append(mode.getTargets().get(0).getTargetName());
        if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}

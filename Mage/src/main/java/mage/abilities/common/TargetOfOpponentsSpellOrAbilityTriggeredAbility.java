package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * "Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls,"
 *  AND
 *  "Whenever you become the target of a spell or ability an opponent controls,"
 *
 * @author Alex-Vasile
 */
public class TargetOfOpponentsSpellOrAbilityTriggeredAbility extends TriggeredAbilityImpl {

    private Boolean onlyController = Boolean.FALSE;

    public TargetOfOpponentsSpellOrAbilityTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    // NOTE: Using Boolean instead of boolean in order to have a second constructor with (Effect, "boolean") signature
    public TargetOfOpponentsSpellOrAbilityTriggeredAbility(Effect effect, Boolean onlyController) {
        this(effect, false, onlyController);
    }

    public TargetOfOpponentsSpellOrAbilityTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, Boolean.FALSE);
    }

    public TargetOfOpponentsSpellOrAbilityTriggeredAbility(Effect effect, boolean optional, Boolean onlyController) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.onlyController = onlyController;
        if (this.onlyController) {
            setTriggerPhrase("Whenever you become the target of a spell or ability an opponent controls, ");
        } else {
            setTriggerPhrase("Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls, ");
        }
    }

    private TargetOfOpponentsSpellOrAbilityTriggeredAbility(final TargetOfOpponentsSpellOrAbilityTriggeredAbility ability) {
        super(ability);
        this.onlyController = ability.onlyController;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        Player targetter = game.getPlayer(event.getPlayerId());
        if (controller == null || targetter == null || controller.getId().equals(targetter.getId())) {
            return false;
        }

        // Check if player was targeted
        if (controller.getId().equals(event.getTargetId())) {
            // Add target for effects which need it (e.g. the counter effect from AmuletOfSafekeeping)
            this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
            return true;
        }

        // If only the controller is
        if (this.onlyController) {
            return false;
        }

        // Check if permanent was targeted
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null || !controller.getId().equals(permanent.getControllerId())) {
            return false;
        }

        // Add target for effects which need it (e.g. the counter effect from AmuletOfSafekeeping)
        this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
        return false;
    }

    @Override
    public TargetOfOpponentsSpellOrAbilityTriggeredAbility copy() {
        return new TargetOfOpponentsSpellOrAbilityTriggeredAbility(this);
    }
}

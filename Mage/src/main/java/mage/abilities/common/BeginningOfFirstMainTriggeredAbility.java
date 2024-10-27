package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */

public class BeginningOfFirstMainTriggeredAbility extends TriggeredAbilityImpl {

    private final TargetController targetController;
    private final boolean setTargetPointer;

    public BeginningOfFirstMainTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, isOptional, false);
    }

    public BeginningOfFirstMainTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional, boolean setTargetPointer) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(generateTriggerPhrase());
    }

    protected BeginningOfFirstMainTriggeredAbility(final BeginningOfFirstMainTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BeginningOfFirstMainTriggeredAbility copy() {
        return new BeginningOfFirstMainTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PRECOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                boolean yours = event.getPlayerId().equals(this.controllerId);
                if (yours && setTargetPointer) {
                    if (getTargets().isEmpty()) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                    }
                }
                return yours;
            case OPPONENT:
                if (game.getPlayer(this.controllerId).hasOpponent(event.getPlayerId(), game)) {
                    if (setTargetPointer) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                    }
                    return true;
                }
                break;
            case ANY:
                if (setTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
        }
        return false;
    }

    private String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your first main phase, ";
            case OPPONENT:
                return "At the beginning of each opponent's first main phase, ";
            case ANY:
                return "At the beginning of each player's first main phase, ";
        }
        return "";
    }

}

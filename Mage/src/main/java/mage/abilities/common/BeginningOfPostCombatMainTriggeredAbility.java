package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class BeginningOfPostCombatMainTriggeredAbility extends TriggeredAbilityImpl {

    private final TargetController targetController;
    private final boolean setTargetPointer;

    public BeginningOfPostCombatMainTriggeredAbility(Effect effect, TargetController targetController, boolean isOptional) {
        this(Zone.BATTLEFIELD, effect, targetController, isOptional, false);
    }

    public BeginningOfPostCombatMainTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean isOptional, boolean setTargetPointer) {
        super(zone, effect, isOptional);
        this.targetController = targetController;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(generateTriggerPhrase());
    }

    protected BeginningOfPostCombatMainTriggeredAbility(final BeginningOfPostCombatMainTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BeginningOfPostCombatMainTriggeredAbility copy() {
        return new BeginningOfPostCombatMainTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.POSTCOMBAT_MAIN_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                if (!isControlledBy(event.getPlayerId())) {
                    return false;
                }
                if (setTargetPointer && getTargets().isEmpty()) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            case OPPONENT:
                if (!game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
                    return false;
                }
                if (setTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            case ANY:
                if (setTargetPointer) {
                    for (Effect effect : this.getEffects()) {
                        effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                    }
                }
                return true;
            case ENCHANTED:
                Permanent permanent = getSourcePermanentIfItStillExists(game);
                if (permanent == null || !game.isActivePlayer(permanent.getAttachedTo())) {
                    return false;
                }
                if (getTargets().isEmpty()) {
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
        }
        return false;
    }

    private String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of each of your postcombat main phases, ";
            case OPPONENT:
                return "At the beginning of each of your opponent's postcombat main phases, ";
            case ANY:
                return "At the beginning of each postcombat main phase, ";
            case ENCHANTED:
                return "At the beginning of each of enchanted player's postcombat main phases, ";
        }
        return "";
    }

}

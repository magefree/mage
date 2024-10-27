package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.abilities.triggers.AtStepTriggeredAbility;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class BeginningOfPostCombatMainTriggeredAbility extends AtStepTriggeredAbility {

    // Note: new card implementations probably use BeginningOfSecondMainTriggeredAbility instead
    public BeginningOfPostCombatMainTriggeredAbility(Effect effect, TargetController targetController, boolean optional) {
        super(Zone.BATTLEFIELD, targetController, effect, optional);
    }

    protected BeginningOfPostCombatMainTriggeredAbility(final BeginningOfPostCombatMainTriggeredAbility ability) {
        super(ability);
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
    protected String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of each of your postcombat main phases, ";
            case OPPONENT:
                return "At the beginning of each of your opponent's postcombat main phases, ";
            case ANY:
                return "At the beginning of each postcombat main phase, ";
            case ENCHANTED:
                return "At the beginning of each of enchanted player's postcombat main phases, ";
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in BeginningOfPostCombatMainTriggeredAbility: " + targetController);
        }
    }

}

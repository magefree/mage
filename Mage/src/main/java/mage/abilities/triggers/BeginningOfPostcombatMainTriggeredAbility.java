package mage.abilities.triggers;

import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class BeginningOfPostcombatMainTriggeredAbility extends AtStepTriggeredAbility {

    /**
     * At the beginning of your postcombat main phase
     */
    public BeginningOfPostcombatMainTriggeredAbility(Effect effect, boolean optional) {
        this(TargetController.YOU, effect, optional);
    }

    // Note: new card implementations probably use BeginningOfSecondMainTriggeredAbility instead
    public BeginningOfPostcombatMainTriggeredAbility(TargetController targetController, Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, targetController, effect, optional);
    }

    protected BeginningOfPostcombatMainTriggeredAbility(final BeginningOfPostcombatMainTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfPostcombatMainTriggeredAbility copy() {
        return new BeginningOfPostcombatMainTriggeredAbility(this);
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

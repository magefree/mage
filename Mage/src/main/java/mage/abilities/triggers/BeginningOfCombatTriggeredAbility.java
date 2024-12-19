package mage.abilities.triggers;

import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

public class BeginningOfCombatTriggeredAbility extends AtStepTriggeredAbility {

    /**
     * At the beginning of combat on your turn (optional = false)
     */
    public BeginningOfCombatTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    /**
     * At the beginning of combat on your turn
     */
    public BeginningOfCombatTriggeredAbility(Effect effect, boolean optional) {
        this(TargetController.YOU, effect, optional);
    }

    public BeginningOfCombatTriggeredAbility(TargetController targetController, Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, targetController, effect, optional);
    }

    public BeginningOfCombatTriggeredAbility(Zone zone, TargetController targetController, Effect effect, boolean optional) {
        super(zone, targetController, effect, optional);
    }

    protected BeginningOfCombatTriggeredAbility(final BeginningOfCombatTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeginningOfCombatTriggeredAbility copy() {
        return new BeginningOfCombatTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BEGIN_COMBAT_STEP_PRE;
    }

    @Override
    protected String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of combat on your turn, ";
            case OPPONENT:
                return "At the beginning of combat on each opponent's turn, ";
            case EACH_PLAYER:
                return "At the beginning of combat on each player's turn, ";
            case ANY:
                return "At the beginning of each combat, ";
            case ENCHANTED:
                return "At the beginning of combat on enchanted player's turn, ";
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in BeginningOfCombatTriggeredAbility: " + targetController);
        }
    }

}

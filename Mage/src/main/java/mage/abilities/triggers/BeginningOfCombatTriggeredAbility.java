package mage.abilities.triggers;

import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

public class BeginningOfCombatTriggeredAbility extends AtStepTriggeredAbility {

    public BeginningOfCombatTriggeredAbility(Effect effect, TargetController targetController, boolean optional) {
        this(Zone.BATTLEFIELD, effect, targetController, optional);
    }

    public BeginningOfCombatTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean optional) {
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
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in BeginningOfCombatTriggeredAbility: " + targetController);
        }
    }

}

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
public class EndOfCombatTriggeredAbility extends AtStepTriggeredAbility {

    public EndOfCombatTriggeredAbility(Effect effect, boolean optional) {
        this(effect, TargetController.ANY, optional);
    }

    public EndOfCombatTriggeredAbility(Effect effect, TargetController targetController, boolean optional) {
        super(Zone.BATTLEFIELD, targetController, effect, optional);
    }

    protected EndOfCombatTriggeredAbility(final EndOfCombatTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EndOfCombatTriggeredAbility copy() {
        return new EndOfCombatTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE;
    }

    @Override
    protected String generateTriggerPhrase() {
        switch (targetController) {
            case ANY:
                return "At end of combat, ";
            case YOU:
                return "At end of combat on your turn, ";
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in EndOfCombatTriggeredAbility: " + targetController);
        }
    }
}

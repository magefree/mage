package mage.abilities.triggers;

import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class BeginningOfFirstMainTriggeredAbility extends AtStepTriggeredAbility {

    /**
     * At the beginning of your first main phase
     */
    public BeginningOfFirstMainTriggeredAbility(Effect effect, boolean optional) {
        this(TargetController.YOU, effect, optional);
    }

    public BeginningOfFirstMainTriggeredAbility(TargetController targetController, Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, targetController, effect, optional);
    }

    public BeginningOfFirstMainTriggeredAbility(Zone zone, TargetController targetController, Effect effect, boolean optional) {
        super(zone, targetController, effect, optional);
    }

    protected BeginningOfFirstMainTriggeredAbility(final BeginningOfFirstMainTriggeredAbility ability) {
        super(ability);
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
    protected String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "At the beginning of your first main phase, ";
            case EACH_PLAYER:
                return "At the beginning of each player's first main phase, ";
            default:
                throw new UnsupportedOperationException("Unsupported TargetController in BeginningOfFirstMainTriggeredAbility: " + targetController);
        }
    }

}

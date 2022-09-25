package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class BecomesClassLevelTriggeredAbility extends TriggeredAbilityImpl {

    private final int level;

    public BecomesClassLevelTriggeredAbility(Effect effect, int level) {
        super(Zone.BATTLEFIELD, effect);
        this.level = level;
        setTriggerPhrase("When this Class becomes level " + level + ", " );
    }

    private BecomesClassLevelTriggeredAbility(final BecomesClassLevelTriggeredAbility ability) {
        super(ability);
        this.level = ability.level;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINS_CLASS_LEVEL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getAmount() == level && event.getSourceId().equals(getSourceId());
    }

    @Override
    public BecomesClassLevelTriggeredAbility copy() {
        return new BecomesClassLevelTriggeredAbility(this);
    }
}

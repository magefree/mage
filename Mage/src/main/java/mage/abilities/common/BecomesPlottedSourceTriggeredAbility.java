package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Susucr
 */
public class BecomesPlottedSourceTriggeredAbility extends TriggeredAbilityImpl {

    public BecomesPlottedSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.EXILED, effect, optional);
        setTriggerPhrase("When {this} becomes plotted, ");
        replaceRuleText = true;
    }

    public BecomesPlottedSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    protected BecomesPlottedSourceTriggeredAbility(final BecomesPlottedSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BecomesPlottedSourceTriggeredAbility copy() {
        return new BecomesPlottedSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOME_PLOTTED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        return false;
    }
}
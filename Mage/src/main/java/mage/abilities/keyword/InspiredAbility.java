package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * Inspired ability word
 *
 * @author LevelX2
 */
public class InspiredAbility extends TriggeredAbilityImpl {

    public InspiredAbility(Effect effect) {
        this(effect, false);
    }

    public InspiredAbility(Effect effect, boolean optional) {
        this(effect, optional, true);
    }

    public InspiredAbility(Effect effect, boolean optional, boolean isInspired) {
        super(Zone.BATTLEFIELD, effect, optional);
        if (isInspired) {
            setAbilityWord(AbilityWord.INSPIRED);
        }
        setTriggerPhrase("Whenever {this} becomes untapped, ");
    }

    public InspiredAbility(final InspiredAbility ability) {
        super(ability);
    }

    @Override
    public InspiredAbility copy() {
        return new InspiredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId());
    }
}

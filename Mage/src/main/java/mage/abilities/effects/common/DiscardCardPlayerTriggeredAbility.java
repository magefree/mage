package mage.abilities.effects.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author jeffwadsworth
 */

public class DiscardCardPlayerTriggeredAbility extends TriggeredAbilityImpl {

    private SetTargetPointer setTargetPointer;

    public DiscardCardPlayerTriggeredAbility(Effect effect, boolean isOptional) {
        this(effect, isOptional, SetTargetPointer.NONE);
    }

    public DiscardCardPlayerTriggeredAbility(Effect effect, boolean isOptional, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect, isOptional);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever a player discards a card, ");
    }

    private DiscardCardPlayerTriggeredAbility(final DiscardCardPlayerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DiscardCardPlayerTriggeredAbility copy() {
        return new DiscardCardPlayerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DISCARDED_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }
}
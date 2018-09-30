/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author fireshoes
 */
public class SpellCounteredControllerTriggeredAbility extends TriggeredAbilityImpl {

    public SpellCounteredControllerTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public SpellCounteredControllerTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public SpellCounteredControllerTriggeredAbility(final SpellCounteredControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpellCounteredControllerTriggeredAbility copy() {
        return new SpellCounteredControllerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.COUNTERED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        StackObject stackObjectThatCountered = game.getStack().getStackObject(event.getSourceId());
        if (stackObjectThatCountered == null) {
            stackObjectThatCountered = (StackObject) game.getLastKnownInformation(event.getSourceId(), Zone.STACK);
        }
        if (stackObjectThatCountered != null && stackObjectThatCountered.isControlledBy(getControllerId())) {
            StackObject counteredStackObject = (StackObject) game.getLastKnownInformation(event.getTargetId(), Zone.STACK);
            return (counteredStackObject instanceof Spell);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability you control counters a spell, " + super.getRule();
    }
}

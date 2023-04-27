package mage.abilities.effects.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class CreatureExploresTriggeredAbility extends TriggeredAbilityImpl {

    public CreatureExploresTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever a creature you control explores, ");
    }

    public CreatureExploresTriggeredAbility(final CreatureExploresTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXPLORED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (creature != null) {
            return creature.isControlledBy(getControllerId());
        }
        return false;
    }

    @Override
    public CreatureExploresTriggeredAbility copy() {
        return new CreatureExploresTriggeredAbility(this);
    }
}

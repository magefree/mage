package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author stravant
 */
public class ExertCreatureControllerTriggeredAbility extends TriggeredAbilityImpl {

    public ExertCreatureControllerTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever you exert a creature, ");
    }

    public ExertCreatureControllerTriggeredAbility(final ExertCreatureControllerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_EXERTED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean weAreExerting = isControlledBy(event.getPlayerId());
        Permanent exerted = game.getPermanent(event.getTargetId());
        boolean exertedIsCreature = (exerted != null) && exerted.isCreature(game);
        return weAreExerting && exertedIsCreature;
    }

    @Override
    public ExertCreatureControllerTriggeredAbility copy() {
        return new ExertCreatureControllerTriggeredAbility(this);
    }
}

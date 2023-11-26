package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author stravant
 */
public class ExertCreatureControllerTriggeredAbility extends TriggeredAbilityImpl {

    protected final boolean setTargetPointer;
    public ExertCreatureControllerTriggeredAbility(Effect effect) {
        this(effect, false);
    }
    public ExertCreatureControllerTriggeredAbility(Effect effect, boolean setTargetPointer) {
        super(Zone.BATTLEFIELD, effect);
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("Whenever you exert a creature, ");
    }

    protected ExertCreatureControllerTriggeredAbility(final ExertCreatureControllerTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
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
        if (weAreExerting && exertedIsCreature) {
            if (setTargetPointer) {
                getAllEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public ExertCreatureControllerTriggeredAbility copy() {
        return new ExertCreatureControllerTriggeredAbility(this);
    }
}

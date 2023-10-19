package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.abilities.keyword.CyclingAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;

/**
 * @author Plopman
 */
public class CycleTriggeredAbility extends ZoneChangeTriggeredAbility {

    public CycleTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, "When you cycle {this}, ", optional);
        this.replaceRuleText = true; // default true to replace "{this}" with "it"
    }

    public CycleTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    protected CycleTriggeredAbility(CycleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            StackObject object = game.getStack().getStackObject(event.getSourceId());
            if (object != null && object.getStackAbility() instanceof CyclingAbility) {
                this.getEffects().setValue("cycleCosts", object.getStackAbility().getCosts());
                return true;
            }
        }
        return false;
    }

    @Override
    public CycleTriggeredAbility copy() {
        return new CycleTriggeredAbility(this);
    }
}

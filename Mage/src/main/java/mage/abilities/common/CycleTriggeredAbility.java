package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.abilities.keyword.CyclingAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.util.CardUtil;

/**
 * @author Plopman
 */
public class CycleTriggeredAbility extends ZoneChangeTriggeredAbility {

    public CycleTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.ALL, effect, "When you cycle this card, ", optional);
        this.withRuleTextReplacement(true); // default true to replace "{this}" with "it"
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
        if (!event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        StackObject object = game.getStack().getStackObject(event.getSourceId());
        if (object == null || !(object.getStackAbility() instanceof CyclingAbility)) {
            return false;
        }
        this.getEffects().setValue("cycleCosts", object.getStackAbility().getCosts());
        this.getEffects().setValue("cycleXValue", CardUtil.getSourceCostsTag(game, object.getStackAbility(), "X", 0));
        return true;
    }

    @Override
    public CycleTriggeredAbility copy() {
        return new CycleTriggeredAbility(this);
    }
}

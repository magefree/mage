package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author North
 */
public class BecomesTargetSourceTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterStackObject filter;
    private final SetTargetPointer setTargetPointer;

    public BecomesTargetSourceTriggeredAbility(Effect effect) {
        this(effect, StaticFilters.FILTER_SPELL_OR_ABILITY_A);
    }

    public BecomesTargetSourceTriggeredAbility(Effect effect, FilterStackObject filter) {
        this(effect, filter, SetTargetPointer.NONE, false);
    }

    public BecomesTargetSourceTriggeredAbility(Effect effect, FilterStackObject filter, SetTargetPointer setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(getWhen() + "{this} becomes the target of " + filter.getMessage() + ", ");
        this.replaceRuleText = true; // default true to replace "{this}" with "it"
    }

    protected BecomesTargetSourceTriggeredAbility(final BecomesTargetSourceTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BecomesTargetSourceTriggeredAbility copy() {
        return new BecomesTargetSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(getSourceId())) {
            return false;
        }
        StackObject targetingObject = CardUtil.getTargetingStackObject(event, game);
        if (targetingObject == null || !filter.match(targetingObject, getControllerId(), this, game)) {
            return false;
        }
        if (CardUtil.checkTargetedEventAlreadyUsed(this.id.toString(), targetingObject, event, game)) {
            return false;
        }
        switch (setTargetPointer) {
            case PLAYER:
                this.getAllEffects().setTargetPointer(new FixedTarget(targetingObject.getControllerId(), game));
                break;
            case SPELL:
                this.getAllEffects().setTargetPointer(new FixedTarget(targetingObject.getId()));
                break;
            case NONE:
                break;
            default:
                throw new IllegalArgumentException("Unsupported SetTargetPointer in BecomesTargetSourceTriggeredAbility");
        }
        return true;
    }
}

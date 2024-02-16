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
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author LoneFox
 */
public class BecomesTargetAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterStackObject filter;
    private final SetTargetPointer setTargetPointer;

    public BecomesTargetAttachedTriggeredAbility(Effect effect) {
        this(effect, StaticFilters.FILTER_SPELL_OR_ABILITY_A, SetTargetPointer.NONE, false);
    }

    public BecomesTargetAttachedTriggeredAbility(Effect effect, FilterStackObject filter, SetTargetPointer setTargetPointer, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase("When enchanted creature becomes the target of " + filter.getMessage() + ", ");
    }

    protected BecomesTargetAttachedTriggeredAbility(final BecomesTargetAttachedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public BecomesTargetAttachedTriggeredAbility copy() {
        return new BecomesTargetAttachedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(sourceId);
        if (enchantment == null || enchantment.getAttachedTo() == null || !event.getTargetId().equals(enchantment.getAttachedTo())) {
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
                throw new IllegalArgumentException("Unsupported SetTargetPointer in BecomesTargetAttachedTriggeredAbility");
        }
        return true;
    }
}

package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 * @author LoneFox
 */
public class BecomesTargetAttachedTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterStackObject filter;

    public BecomesTargetAttachedTriggeredAbility(Effect effect) {
        this(effect, StaticFilters.FILTER_SPELL_OR_ABILITY_A, false);
    }

    public BecomesTargetAttachedTriggeredAbility(Effect effect, FilterStackObject filter, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        setTriggerPhrase("When enchanted creature becomes the target of " + filter.getMessage() + ", ");
    }

    protected BecomesTargetAttachedTriggeredAbility(final BecomesTargetAttachedTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
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
        return CardUtil.checkTargetMap(this.id, targetingObject, event, game);
    }
}

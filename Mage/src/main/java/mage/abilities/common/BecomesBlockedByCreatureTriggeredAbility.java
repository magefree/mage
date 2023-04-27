package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author North
 */
public class BecomesBlockedByCreatureTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCreaturePermanent filter;

    public BecomesBlockedByCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, StaticFilters.FILTER_PERMANENT_CREATURE, optional);
    }

    public BecomesBlockedByCreatureTriggeredAbility(Effect effect, FilterCreaturePermanent filter, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        setTriggerPhrase("Whenever {this} becomes blocked by " + CardUtil.addArticle(filter.getMessage()) + ", ");
    }

    public BecomesBlockedByCreatureTriggeredAbility(final BecomesBlockedByCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getTargetId().equals(this.getSourceId())) {
            return false;
        }
        if (!filter.match(game.getPermanent(event.getSourceId()), getControllerId(), this, game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
        return true;
    }

    @Override
    public BecomesBlockedByCreatureTriggeredAbility copy() {
        return new BecomesBlockedByCreatureTriggeredAbility(this);
    }
}

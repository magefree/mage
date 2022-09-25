package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * @author awjackson
 */
public class BlocksOrBlockedByCreatureSourceTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;

    public BlocksOrBlockedByCreatureSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public BlocksOrBlockedByCreatureSourceTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, filter, false);
    }

    public BlocksOrBlockedByCreatureSourceTriggeredAbility(Effect effect, boolean optional) {
        this(effect, StaticFilters.FILTER_PERMANENT_CREATURE, optional);
    }

    public BlocksOrBlockedByCreatureSourceTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        setTriggerPhrase("Whenever {this} blocks or becomes blocked by " + CardUtil.addArticle(filter.getMessage()) + ", ");
    }

    public BlocksOrBlockedByCreatureSourceTriggeredAbility(final BlocksOrBlockedByCreatureSourceTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent otherCreature = null;
        if (this.getSourceId().equals(event.getSourceId())) {
            otherCreature = game.getPermanent(event.getTargetId());
        } else if (this.getSourceId().equals(event.getTargetId())) {
            otherCreature = game.getPermanent(event.getSourceId());
        } else {
            return false;
        }
        if (!filter.match(otherCreature, getControllerId(), this, game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(otherCreature, game));
        return true;
    }

    @Override
    public BlocksOrBlockedByCreatureSourceTriggeredAbility copy() {
        return new BlocksOrBlockedByCreatureSourceTriggeredAbility(this);
    }
}

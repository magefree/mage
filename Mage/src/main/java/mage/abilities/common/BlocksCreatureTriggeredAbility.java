package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Hiddevb
 */
public class BlocksCreatureTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCreaturePermanent filter;

    public BlocksCreatureTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = StaticFilters.FILTER_PERMANENT_CREATURE;
    }

    public BlocksCreatureTriggeredAbility(Effect effect, FilterCreaturePermanent filter, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public BlocksCreatureTriggeredAbility(final BlocksCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId())
                && !filter.match(game.getPermanent(event.getSourceId()), game);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} blocks "
                + (filter.getMessage().startsWith("an ") ? "" : "a ")
                + filter.getMessage() + ", " ;
    }

    @Override
    public BlocksCreatureTriggeredAbility copy() {
        return new BlocksCreatureTriggeredAbility(this);
    }
}
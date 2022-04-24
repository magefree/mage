package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 * @author Hiddevb
 */
public class BlocksCreatureTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterCreaturePermanent filter;

    public BlocksCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, StaticFilters.FILTER_PERMANENT_CREATURE, optional);
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
                && filter.match(game.getPermanent(event.getTargetId()), getControllerId(), this, game);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} blocks " + CardUtil.addArticle(filter.getMessage()) + ", ";
    }

    @Override
    public BlocksCreatureTriggeredAbility copy() {
        return new BlocksCreatureTriggeredAbility(this);
    }
}

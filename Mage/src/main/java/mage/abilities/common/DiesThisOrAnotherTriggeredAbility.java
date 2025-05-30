package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 * @author noxx
 */
public class DiesThisOrAnotherTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterPermanent filter;
    private boolean applyFilterOnSource = false;

    public DiesThisOrAnotherTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, StaticFilters.FILTER_PERMANENT_CREATURE);
    }

    public DiesThisOrAnotherTriggeredAbility(Effect effect, boolean optional, FilterPermanent filter) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        String filterMessage = filter.getMessage();
        if (filterMessage.startsWith("a ")) {
            filterMessage = filterMessage.substring(2);
        }
        setTriggerPhrase("Whenever {this} or " + (filterMessage.startsWith("another") ? "" : "another ") + filterMessage + " dies, ");
        setLeavesTheBattlefieldTrigger(true);
    }

    protected DiesThisOrAnotherTriggeredAbility(final DiesThisOrAnotherTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.applyFilterOnSource = ability.applyFilterOnSource;
    }

    public DiesThisOrAnotherTriggeredAbility setApplyFilterOnSource(boolean applyFilterOnSource) {
        this.applyFilterOnSource = applyFilterOnSource;
        return this;
    }

    @Override
    public DiesThisOrAnotherTriggeredAbility copy() {
        return new DiesThisOrAnotherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent() || zEvent.getTarget() == null) {
            return false;
        }
        // TODO: remove applyFilterOnSource workaround for Basri's Lieutenant
        if ((applyFilterOnSource || !zEvent.getTarget().getId().equals(this.getSourceId()))
                && !filter.match(zEvent.getTarget(), getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setValue("creatureDied", zEvent.getTarget());
        return true;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}

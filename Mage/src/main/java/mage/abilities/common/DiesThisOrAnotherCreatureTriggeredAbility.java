package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 * @author noxx
 */
public class DiesThisOrAnotherCreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;
    private boolean applyFilterOnSource = false;

    public DiesThisOrAnotherCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreaturePermanent());
    }

    public DiesThisOrAnotherCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        super(Zone.ALL, effect, optional); // Needs "ALL" if the source itself should trigger or multiple (incl. source go to grave)
        this.filter = filter;
    }

    public DiesThisOrAnotherCreatureTriggeredAbility(DiesThisOrAnotherCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.applyFilterOnSource = ability.applyFilterOnSource;
    }

    public DiesThisOrAnotherCreatureTriggeredAbility setApplyFilterOnSource(boolean applyFilterOnSource) {
        this.applyFilterOnSource = applyFilterOnSource;
        return this;
    }

    @Override
    public DiesThisOrAnotherCreatureTriggeredAbility copy() {
        return new DiesThisOrAnotherCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            if (zEvent.getTarget() != null) {
                if (!applyFilterOnSource && zEvent.getTarget().getId().equals(this.getSourceId())) {
                    return true;
                } else {
                    if (filter.match(zEvent.getTarget(), getSourceId(), getControllerId(), game)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, event, game);
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another " + filter.getMessage() + " dies, " + super.getRule();
    }
}

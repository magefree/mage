package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

public class DiesThisOrAnotherCreatureTriggeredAbility extends TriggeredAbilityImpl<DiesThisOrAnotherCreatureTriggeredAbility> {

    protected FilterCreaturePermanent filter;

    public DiesThisOrAnotherCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreaturePermanent());
    }

    public DiesThisOrAnotherCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public DiesThisOrAnotherCreatureTriggeredAbility(DiesThisOrAnotherCreatureTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DiesThisOrAnotherCreatureTriggeredAbility copy() {
        return new DiesThisOrAnotherCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Constants.Zone.BATTLEFIELD && zEvent.getToZone() == Constants.Zone.GRAVEYARD) {
                Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
                if (permanent != null) {
                    if (permanent.getId().equals(this.getSourceId())) {
                        return true;
                    } else {
                        if (filter.match(permanent)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature dies, " + super.getRule();
    }
}
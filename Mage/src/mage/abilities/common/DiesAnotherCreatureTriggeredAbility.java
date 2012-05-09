package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * noxx
 */
public class DiesAnotherCreatureTriggeredAbility extends TriggeredAbilityImpl<DiesAnotherCreatureTriggeredAbility> {

    protected FilterCreaturePermanent filter;
    protected boolean nontoken;

    public DiesAnotherCreatureTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreaturePermanent());
    }

    public DiesAnotherCreatureTriggeredAbility(Effect effect, boolean optional, boolean nontoken) {
        this(effect, optional, new FilterCreaturePermanent(), nontoken);
    }

    public DiesAnotherCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        this(effect, optional, filter, false);
    }

    public DiesAnotherCreatureTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter, boolean nontoken) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
        this.nontoken = nontoken;
    }

    public DiesAnotherCreatureTriggeredAbility(DiesAnotherCreatureTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.nontoken = ability.nontoken;
    }

    @Override
    public DiesAnotherCreatureTriggeredAbility copy() {
        return new DiesAnotherCreatureTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;

            UUID sourceId = getSourceId();
            if (game.getPermanent(sourceId) == null) {
                if (game.getLastKnownInformation(sourceId, Constants.Zone.BATTLEFIELD) == null) {
                    return false;
                }
            }

            if (zEvent.getFromZone() == Constants.Zone.BATTLEFIELD && zEvent.getToZone() == Constants.Zone.GRAVEYARD) {
                Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
                if (permanent != null) {
                    if (permanent.getId().equals(this.getSourceId())) {
                        return false;
                    }
                    if (nontoken && permanent instanceof PermanentToken) {
                        return false;
                    }
                    if (filter.match(permanent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        if (nontoken) {
            return "Whenever another nontoken creature dies, " + super.getRule();
        }
        return "Whenever another creature dies, " + super.getRule();
    }
}
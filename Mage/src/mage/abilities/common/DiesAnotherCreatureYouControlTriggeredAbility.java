package mage.abilities.common;

import mage.Constants;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

public class DiesAnotherCreatureYouControlTriggeredAbility extends TriggeredAbilityImpl<DiesAnotherCreatureYouControlTriggeredAbility> {
    protected FilterCreaturePermanent filter;

    public DiesAnotherCreatureYouControlTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreaturePermanent());
    }
    
    public DiesAnotherCreatureYouControlTriggeredAbility(Effect effect, boolean optional, FilterCreaturePermanent filter) {
        super(Constants.Zone.BATTLEFIELD, effect, optional);
        this.filter = filter;
    }

    public DiesAnotherCreatureYouControlTriggeredAbility(DiesAnotherCreatureYouControlTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DiesAnotherCreatureYouControlTriggeredAbility copy() {
        return new DiesAnotherCreatureYouControlTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {

            UUID sourceId = getSourceId();
            if (game.getPermanent(sourceId) == null) {
                if (game.getLastKnownInformation(sourceId, Constants.Zone.BATTLEFIELD) == null) {
                    return false;
                }
            }

            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            Permanent permanent = zEvent.getTarget();

            if (permanent != null && permanent.getCardType().contains(Constants.CardType.CREATURE) &&
                    zEvent.isDiesEvent() &&
                    permanent.getControllerId().equals(this.getControllerId()) && filter != null &&
                    filter.match(permanent)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another creature you control dies, " + super.getRule();
    }
}
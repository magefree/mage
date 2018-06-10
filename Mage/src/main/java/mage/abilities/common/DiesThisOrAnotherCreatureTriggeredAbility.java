
package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * @author noxx
 */
public class DiesThisOrAnotherCreatureTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreaturePermanent filter;

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
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        Permanent sourcePermanent = null;
        if (game.getState().getZone(getSourceId()) == Zone.BATTLEFIELD) {
            sourcePermanent = game.getPermanent(getSourceId());
        } else {
            if (game.getShortLivingLKI(getSourceId(), Zone.BATTLEFIELD)) {
                sourcePermanent = (Permanent) game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD);
            }
        }
        if (sourcePermanent == null) {
            return false;
        }
        return hasSourceObjectAbility(game, sourcePermanent, event);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;

        if (game.getPermanentOrLKIBattlefield(getSourceId()) == null) {
            return false;
        }

        if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
            if (zEvent.getTarget() != null) {
                if (zEvent.getTarget().getId().equals(this.getSourceId())) {
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
    public String getRule() {
        return "Whenever {this} or another " + filter.getMessage() + " dies, " + super.getRule();
    }
}

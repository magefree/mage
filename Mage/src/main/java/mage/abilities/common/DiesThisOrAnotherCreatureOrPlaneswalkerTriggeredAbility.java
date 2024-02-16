
package mage.abilities.common;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 * @author noxx
 */
public class DiesThisOrAnotherCreatureOrPlaneswalkerTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterCreatureOrPlaneswalkerPermanent filter;

    public DiesThisOrAnotherCreatureOrPlaneswalkerTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, new FilterCreatureOrPlaneswalkerPermanent());
    }

    public DiesThisOrAnotherCreatureOrPlaneswalkerTriggeredAbility(Effect effect, boolean optional, FilterCreatureOrPlaneswalkerPermanent filter) {
        super(Zone.ALL, effect, optional); // Needs "ALL" if the source itself should trigger or multiple (incl. source go to grave)
        this.filter = filter;
        setTriggerPhrase("Whenever {this} or another " + filter.getMessage() + " dies, ");
    }

    public DiesThisOrAnotherCreatureOrPlaneswalkerTriggeredAbility(DiesThisOrAnotherCreatureOrPlaneswalkerTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
    }

    @Override
    public DiesThisOrAnotherCreatureOrPlaneswalkerTriggeredAbility copy() {
        return new DiesThisOrAnotherCreatureOrPlaneswalkerTriggeredAbility(this);
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
                if (zEvent.getTarget().getId().equals(this.getSourceId())) {
                    return true;
                } else {
                    if (filter.match(zEvent.getTarget(), getControllerId(), this, game)) {
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
}

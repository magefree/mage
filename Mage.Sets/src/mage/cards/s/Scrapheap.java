
package mage.cards.s;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class Scrapheap extends CardImpl {

    public Scrapheap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever an artifact or enchantment is put into your graveyard from the battlefield, you gain 1 life.
        this.addAbility(new ScrapheapTriggeredAbility());
    }

    private Scrapheap(final Scrapheap card) {
        super(card);
    }

    @Override
    public Scrapheap copy() {
        return new Scrapheap(this);
    }
}

class ScrapheapTriggeredAbility extends TriggeredAbilityImpl {

    @Override
    public ScrapheapTriggeredAbility copy() {
        return new ScrapheapTriggeredAbility(this);
    }
    
    public ScrapheapTriggeredAbility(final ScrapheapTriggeredAbility ability){
        super(ability);
    }
    
    public ScrapheapTriggeredAbility(){
       super(Zone.BATTLEFIELD, new GainLifeEffect(1));
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null && permanent.isOwnedBy(this.getControllerId())) {
                if (StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT.match(permanent, controllerId, this, game)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever an artifact or enchantment is put into your graveyard from the battlefield, you gain 1 life.";
    }
}

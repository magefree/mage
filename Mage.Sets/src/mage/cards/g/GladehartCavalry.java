
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class GladehartCavalry extends CardImpl {

    public GladehartCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Gladehart Cavalry enters the battlefield, support 6.
        this.addAbility(new SupportAbility(this, 6));
        
        // Whenever a creature you control with a +1/+1 counter on it dies, you gain 2 life.
        this.addAbility(new GladehartCavalryTriggeredAbility());
    }

    private GladehartCavalry(final GladehartCavalry card) {
        super(card);
    }

    @Override
    public GladehartCavalry copy() {
        return new GladehartCavalry(this);
    }
}

class GladehartCavalryTriggeredAbility extends TriggeredAbilityImpl {

    public GladehartCavalryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(2));
        setTriggerPhrase("Whenever a creature you control with a +1/+1 counter on it dies, ");
    }

    public GladehartCavalryTriggeredAbility(final GladehartCavalryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GladehartCavalryTriggeredAbility copy() {
        return new GladehartCavalryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zoneChangeEvent = (ZoneChangeEvent) event;
        if (zoneChangeEvent.isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null
                    && permanent.isControlledBy(this.getControllerId())
                    && permanent.isCreature(game)
                    && permanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
                return true;
            }
        }
        return false;
    }
}

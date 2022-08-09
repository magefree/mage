
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
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
 * @author North
 */
public final class VillageCannibals extends CardImpl {

    public VillageCannibals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another Human creature dies, put a +1/+1 counter on Village Cannibals.
        this.addAbility(new VillageCannibalsTriggeredAbility());
    }

    private VillageCannibals(final VillageCannibals card) {
        super(card);
    }

    @Override
    public VillageCannibals copy() {
        return new VillageCannibals(this);
    }
}

class VillageCannibalsTriggeredAbility extends TriggeredAbilityImpl {

    public VillageCannibalsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        setTriggerPhrase("Whenever another Human creature dies, ");
    }

    public VillageCannibalsTriggeredAbility(final VillageCannibalsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VillageCannibalsTriggeredAbility copy() {
        return new VillageCannibalsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()) {
            return false;
        }
        Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
        return permanent != null && permanent.isCreature(game) && permanent.hasSubtype(SubType.HUMAN, game)
                && !permanent.getId().equals(this.getSourceId());
    }
}

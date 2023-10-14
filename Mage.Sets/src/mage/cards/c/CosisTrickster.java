
package mage.cards.c;

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
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author North
 */
public final class CosisTrickster extends CardImpl {

    public CosisTrickster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new CosisTricksterTriggeredAbility());
    }

    private CosisTrickster(final CosisTrickster card) {
        super(card);
    }

    @Override
    public CosisTrickster copy() {
        return new CosisTrickster(this);
    }
}

class CosisTricksterTriggeredAbility extends TriggeredAbilityImpl {

    public CosisTricksterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
    }

    private CosisTricksterTriggeredAbility(final CosisTricksterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CosisTricksterTriggeredAbility copy() {
        return new CosisTricksterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LIBRARY_SHUFFLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getOpponents(controllerId).contains(event.getPlayerId());
    }

    @Override
    public String getRule() {
        return "Whenever an opponent shuffles their library, you may put a +1/+1 counter on {this}.";
    }
}

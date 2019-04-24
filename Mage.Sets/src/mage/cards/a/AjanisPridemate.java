

package mage.cards.a;

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
 * @author BetaSteward_at_googlemail.com
 */
public final class AjanisPridemate extends CardImpl {

    public AjanisPridemate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new AjanisPridemateAbility());
    }

    public AjanisPridemate(final AjanisPridemate card) {
        super(card);
    }

    @Override
    public AjanisPridemate copy() {
        return new AjanisPridemate(this);
    }

}

class AjanisPridemateAbility extends TriggeredAbilityImpl {

    public AjanisPridemateAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), true);
    }

    public AjanisPridemateAbility(final AjanisPridemateAbility ability) {
        super(ability);
    }

    @Override
    public AjanisPridemateAbility copy() {
        return new AjanisPridemateAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(controllerId);
    }

    @Override
    public String getRule() {
        return "Whenever you gain life, you may put a +1/+1 counter on {this}.";
    }

}
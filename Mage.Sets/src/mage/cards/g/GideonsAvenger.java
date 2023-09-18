
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class GideonsAvenger extends CardImpl {

    public GideonsAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature an opponent controls becomes tapped, put a +1/+1 counter on Gideon's Avenger.
        this.addAbility(new BecomesTappedTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE));
    }

    private GideonsAvenger(final GideonsAvenger card) {
        super(card);
    }

    @Override
    public GideonsAvenger copy() {
        return new GideonsAvenger(this);
    }
}

package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlamingFistOfficer extends CardImpl {

    public FlamingFistOfficer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.GNOME);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature you control leaves the battlefield, put a +1/+1 counter on Flaming Fist Officer.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));
    }

    private FlamingFistOfficer(final FlamingFistOfficer card) {
        super(card);
    }

    @Override
    public FlamingFistOfficer copy() {
        return new FlamingFistOfficer(this);
    }
}

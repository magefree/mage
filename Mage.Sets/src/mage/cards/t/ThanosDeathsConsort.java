package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ThanosDeathsConsort extends CardImpl {

    public ThanosDeathsConsort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ETERNAL);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever another creature dies, put a +1/+1 counter on Thanos.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));
    }

    private ThanosDeathsConsort(final ThanosDeathsConsort card) {
        super(card);
    }

    @Override
    public ThanosDeathsConsort copy() {
        return new ThanosDeathsConsort(this);
    }
}

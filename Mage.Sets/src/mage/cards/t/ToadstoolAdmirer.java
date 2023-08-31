package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToadstoolAdmirer extends CardImpl {

    public ToadstoolAdmirer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // {3}{G}: Put a +1/+1 counter on Toadstool Admirer.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{3}{G}")
        ));
    }

    private ToadstoolAdmirer(final ToadstoolAdmirer card) {
        super(card);
    }

    @Override
    public ToadstoolAdmirer copy() {
        return new ToadstoolAdmirer(this);
    }
}

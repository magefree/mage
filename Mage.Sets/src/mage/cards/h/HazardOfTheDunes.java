package mage.cards.h;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HazardOfTheDunes extends CardImpl {

    public HazardOfTheDunes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Exhaust -- {6}{G}: Put three +1/+1 counters on this creature.
        this.addAbility(new ExhaustAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), new ManaCostsImpl<>("{6}{G}")
        ));
    }

    private HazardOfTheDunes(final HazardOfTheDunes card) {
        super(card);
    }

    @Override
    public HazardOfTheDunes copy() {
        return new HazardOfTheDunes(this);
    }
}

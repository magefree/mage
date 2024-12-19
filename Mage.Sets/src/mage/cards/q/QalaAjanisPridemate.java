package mage.cards.q;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QalaAjanisPridemate extends CardImpl {

    public QalaAjanisPridemate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Qala, Ajani's Pridemate attacks, other attacking creatures you control get +X/+0 until end of turn, where X is the number of counters on Qala.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
                CountersSourceCount.ANY, StaticValue.get(0), Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES, true
        ).setText("other attacking creatures you control get +X/+0 until end of turn, where X is the number of counters on {this}")));

        // Whenever you gain life, put a +1/+1 counter on Qala.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // {3}{W}: You gain 1 life.
        this.addAbility(new SimpleActivatedAbility(new GainLifeEffect(1), new ManaCostsImpl<>("{3}{W}")));
    }

    private QalaAjanisPridemate(final QalaAjanisPridemate card) {
        super(card);
    }

    @Override
    public QalaAjanisPridemate copy() {
        return new QalaAjanisPridemate(this);
    }
}

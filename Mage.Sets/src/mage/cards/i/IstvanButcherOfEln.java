package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceByPermanentsEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class IstvanButcherOfEln extends CardImpl {

    public IstvanButcherOfEln(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Istvan attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Prevent all damage that would be dealt to Istvan by creatures.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToSourceByPermanentsEffect(StaticFilters.FILTER_PERMANENT_CREATURES)));

        // Whenever a creature an opponent controls dies, put two +1/+1 counters on Istvan.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), false,
            StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));
    }

    private IstvanButcherOfEln(final IstvanButcherOfEln card) {
        super(card);
    }

    @Override
    public IstvanButcherOfEln copy() {
        return new IstvanButcherOfEln(this);
    }
}

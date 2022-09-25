package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author weirddan455
 */
public final class ElectrostaticInfantry extends CardImpl {

    public ElectrostaticInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast an instant or sorcery spell, put a +1/+1 counter on Electrostatic Infantry.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY,
                false
        ));
    }

    private ElectrostaticInfantry(final ElectrostaticInfantry card) {
        super(card);
    }

    @Override
    public ElectrostaticInfantry copy() {
        return new ElectrostaticInfantry(this);
    }
}

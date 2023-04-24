package mage.cards.k;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.abilities.mana.DynamicManaAbility;
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
public final class KamiOfWhisperedHopes extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public KamiOfWhisperedHopes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // If one or more +1/+1 counters would be put on a permanent you control, that many plus one +1/+1 counters are put on that permanent instead.
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(
                StaticFilters.FILTER_CONTROLLED_PERMANENT, CounterType.P1P1
        )));

        // {T}: Add X mana of any one color, where X is Kami of Whispered Hopes's power.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), xValue, new TapSourceCost(), "Add X mana "
                + "of any one color, where X is {this}'s power", true
        ));
    }

    private KamiOfWhisperedHopes(final KamiOfWhisperedHopes card) {
        super(card);
    }

    @Override
    public KamiOfWhisperedHopes copy() {
        return new KamiOfWhisperedHopes(this);
    }
}


package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.OrCost;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class EarthenGoo extends CardImpl {

    public EarthenGoo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cumulative upkeep {R} or {G}
        this.addAbility(new CumulativeUpkeepAbility(new OrCost(
                "{R} or {G}", new ManaCostsImpl<>("{R}"),
                new ManaCostsImpl<>("{G}")
        )));

        // Earthen Goo gets +1/+1 for each age counter on it.
        DynamicValue value = new CountersSourceCount(CounterType.AGE);
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(value, value, Duration.WhileOnBattlefield)
                        .setText("{this} gets +1/+1 for each age counter on it")
        ));
    }

    private EarthenGoo(final EarthenGoo card) {
        super(card);
    }

    @Override
    public EarthenGoo copy() {
        return new EarthenGoo(this);
    }
}

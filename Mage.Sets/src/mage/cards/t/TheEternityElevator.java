package mage.cards.t;

import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEternityElevator extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.CHARGE);

    public TheEternityElevator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // {T}: Add {C}{C}{C}
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(3), new TapSourceCost()));

        // Station
        this.addAbility(new StationAbility());

        // STATION 20+
        // {T}: Add X mana of any one color, where X is the number of charge counters on The Eternity Elevator.
        this.addAbility(new StationLevelAbility(20).withLevelAbility(new DynamicManaAbility(
                Mana.AnyMana(1), xValue, new TapSourceCost(), "add X mana of any one color, " +
                "where X is the number of charge counters on {this}", true
        )));
    }

    private TheEternityElevator(final TheEternityElevator card) {
        super(card);
    }

    @Override
    public TheEternityElevator copy() {
        return new TheEternityElevator(this);
    }
}

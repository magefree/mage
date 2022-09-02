
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class Tidewalker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Island you control");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public Tidewalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Tidewalker enters the battlefield with a time counter on it for each Island you control.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(0), new PermanentsOnBattlefieldCount(filter), true), "with a time counter on it for each Island you control"));
        // Vanishing
        this.addAbility(new VanishingUpkeepAbility(0));
        this.addAbility(new VanishingSacrificeAbility());
        // Tidewalker's power and toughness are each equal to the number of time counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new CountersSourceCount(CounterType.TIME), Duration.EndOfGame)));
    }

    private Tidewalker(final Tidewalker card) {
        super(card);
    }

    @Override
    public Tidewalker copy() {
        return new Tidewalker(this);
    }
}

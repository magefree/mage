package mage.cards.s;

import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class SoulcatchersAerie extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a Bird");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Bird creatures");

    static {
        filter.add(SubType.BIRD.getPredicate());
        filter2.add(SubType.BIRD.getPredicate());
    }

    public SoulcatchersAerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever a Bird is put into your graveyard from the battlefield, put a feather counter on Soulcatchers' Aerie.
        this.addAbility(new PutIntoGraveFromBattlefieldAllTriggeredAbility(new AddCountersSourceEffect(CounterType.FEATHER.createInstance()),
                false, filter, false, true));

        // Bird creatures get +1/+1 for each feather counter on Soulcatchers' Aerie.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(new CountersSourceCount(CounterType.FEATHER),
                new CountersSourceCount(CounterType.FEATHER), Duration.WhileOnBattlefield, filter2, false,
                "Bird creatures get +1/+1 for each feather counter on {this}.")));
    }

    private SoulcatchersAerie(final SoulcatchersAerie card) {
        super(card);
    }

    @Override
    public SoulcatchersAerie copy() {
        return new SoulcatchersAerie(this);
    }
}

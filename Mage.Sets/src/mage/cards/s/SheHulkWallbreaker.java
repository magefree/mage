package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Grath
 */
public final class SheHulkWallbreaker extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.HERO, "Heroes");
    private static final FilterControlledPermanent filter2 =
            new FilterControlledPermanent(SubType.HERO, "a Hero you control");

    public SheHulkWallbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Other Heroes you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                filter, true
        )));

        // Whenever a Hero you control becomes blocked, put a +1/+1 counter on that Hero for each creature blocking it.
        this.addAbility(new BecomesBlockedAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), BlockingCreatureCount.TARGET),
                false, filter2, true
        ));
    }

    private SheHulkWallbreaker(final SheHulkWallbreaker card) {
        super(card);
    }

    @Override
    public SheHulkWallbreaker copy() {
        return new SheHulkWallbreaker(this);
    }
}

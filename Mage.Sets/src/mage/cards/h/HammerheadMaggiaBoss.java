package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
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
public final class HammerheadMaggiaBoss extends CardImpl {

    public HammerheadMaggiaBoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Sacrifice another creature or artifact: Put a +1/+1 counter on Hammerhead.
        this.addAbility(new SimpleActivatedAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT)
        ));
    }

    private HammerheadMaggiaBoss(final HammerheadMaggiaBoss card) {
        super(card);
    }

    @Override
    public HammerheadMaggiaBoss copy() {
        return new HammerheadMaggiaBoss(this);
    }
}

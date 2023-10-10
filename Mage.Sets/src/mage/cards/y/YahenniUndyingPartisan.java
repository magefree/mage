package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class YahenniUndyingPartisan extends CardImpl {

    public YahenniUndyingPartisan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AETHERBORN);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever a creature an opponent controls dies, put a +1/+1 counter on Yahenni, Undying Partisan.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));

        // Sacrifice another creature: Yahenni gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE))
        ));
    }

    private YahenniUndyingPartisan(final YahenniUndyingPartisan card) {
        super(card);
    }

    @Override
    public YahenniUndyingPartisan copy() {
        return new YahenniUndyingPartisan(this);
    }
}

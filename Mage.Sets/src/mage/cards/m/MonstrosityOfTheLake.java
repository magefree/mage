package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.IslandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonstrosityOfTheLake extends CardImpl {

    public MonstrosityOfTheLake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KRAKEN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // When Monstrosity of the Lake enters the battlefield, you may pay {5}. If you do, tap all creatures your opponents control, then put a stun counter on each of those creatures.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                new TapAllEffect(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES), new GenericManaCost(5)
        ).addEffect(new AddCountersAllEffect(
                CounterType.STUN.createInstance(), StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText(", then put a stun counter on each of those creatures"))));

        // Islandcycling {2}
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private MonstrosityOfTheLake(final MonstrosityOfTheLake card) {
        super(card);
    }

    @Override
    public MonstrosityOfTheLake copy() {
        return new MonstrosityOfTheLake(this);
    }
}

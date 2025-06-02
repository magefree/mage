package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.AddCardSubTypeSourceEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhantomTrain extends CardImpl {

    public PhantomTrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Sacrifice another artifact or creature: Put a +1/+1 counter on this Vehicle. It becomes a Spirit artifact creature in addition to its other types until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT)
                        .setText("sacrifice another artifact or creature")
        );
        ability.addEffect(new AddCardTypeSourceEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("it becomes a"));
        ability.addEffect(new AddCardSubTypeSourceEffect(
                Duration.EndOfTurn, true, SubType.SPIRIT
        ).setText(" Spirit artifact creature in addition to its other types until end of turn"));
        this.addAbility(ability);
    }

    private PhantomTrain(final PhantomTrain card) {
        super(card);
    }

    @Override
    public PhantomTrain copy() {
        return new PhantomTrain(this);
    }
}

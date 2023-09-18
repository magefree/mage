package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MukotaiSoulripper extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another artifact or creature");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public MukotaiSoulripper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Mukotai Soulripper attacks, you may sacrifice another artifact or creature. If you do, put a +1/+1 counter on Mukotai Soulripper and it gains menace until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                new SacrificeTargetCost(new TargetControlledPermanent(filter))
        ).addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(false), Duration.EndOfTurn
        ).setText("and it gains menace until end of turn"))));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private MukotaiSoulripper(final MukotaiSoulripper card) {
        super(card);
    }

    @Override
    public MukotaiSoulripper copy() {
        return new MukotaiSoulripper(this);
    }
}

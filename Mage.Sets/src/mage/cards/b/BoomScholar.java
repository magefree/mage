package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoomScholar extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures and Vehicles");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public BoomScholar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Exhaust abilities of other permanents you control cost {2} less to activate.
        this.addAbility(new SimpleStaticAbility(new AbilitiesCostReductionControllerEffect(
                ExhaustAbility.class, "", 2, true
        ).setText("exhaust abilities of other permanents you control cost {2} less to activate")));

        // Exhaust -- {4}{R}{G}: Creatures and Vehicles you control gain trample until end of turn. Put two +1/+1 counters on this creature.
        Ability ability = new ExhaustAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn, filter
        ), new ManaCostsImpl<>("{4}{R}{G}"));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
        this.addAbility(ability);
    }

    private BoomScholar(final BoomScholar card) {
        super(card);
    }

    @Override
    public BoomScholar copy() {
        return new BoomScholar(this);
    }
}

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author muz
 */
public final class PaulAtreidesDucalHeir extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("{this}'s power is 4 or greater");
    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }
    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public PaulAtreidesDucalHeir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Training
        this.addAbility(new TrainingAbility());

        // {T}: Put a +1/+1 counter on each other creature you control. Activate only if Paul Atreides's power is 4 or greater.
        this.addAbility(new ActivateIfConditionActivatedAbility(
            new AddCountersAllEffect(CounterType.P1P1.createInstance(),
            StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE),
            new TapSourceCost(),
            condition
        ));
    }

    private PaulAtreidesDucalHeir(final PaulAtreidesDucalHeir card) {
        super(card);
    }

    @Override
    public PaulAtreidesDucalHeir copy() {
        return new PaulAtreidesDucalHeir(this);
    }
}

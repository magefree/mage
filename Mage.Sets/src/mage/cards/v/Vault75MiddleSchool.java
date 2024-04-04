package mage.cards.v;

import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import java.util.UUID;

/**
 *
 * @author DarkNik
 */
public final class Vault75MiddleSchool extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public Vault75MiddleSchool(UUID ownerId, CardSetInfo setInfo) {

        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Exile all creatures with power 4 or greater.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I,
                new ExileAllEffect(filter)
        );

        // II, III -- Put a +1/+1 counter on each creature you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_III,
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE)
        );

        this.addAbility(sagaAbility);
    }

    private Vault75MiddleSchool(final Vault75MiddleSchool card) {
        super(card);
    }

    @Override
    public Vault75MiddleSchool copy() {
        return new Vault75MiddleSchool(this);
    }

}

package mage.cards.i;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;
import mage.target.targetpointer.ThirdTargetPointer;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class IncrementalGrowth extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(new AnotherTargetPredicate(3));
    }

    public IncrementalGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Put a +1/+1 counter on target creature, two +1/+1 counters on another target 
        // creature, and three +1/+1 counters on a third target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2))
                .setTargetPointer(new SecondTargetPointer()).setText(", two +1/+1 counters on another target creature"));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(3))
                .setTargetPointer(new ThirdTargetPointer()).setText(", and three +1/+1 counters on a third target creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("gets a +1/+1 counter").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).withChooseHint("gets two +1/+1 counters").setTargetTag(2));
        this.getSpellAbility().addTarget(new TargetPermanent(filter).withChooseHint("gets three +1/+1 counters").setTargetTag(3));
    }

    private IncrementalGrowth(final IncrementalGrowth card) {
        super(card);
    }

    @Override
    public IncrementalGrowth copy() {
        return new IncrementalGrowth(this);
    }
}

package mage.cards.m;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class MethodsOfTheMighty extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public MethodsOfTheMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Choose one or more --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(3);

        // * Destroy target artifact.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT));

        // * Destroy target tapped creature.
        this.getSpellAbility().addMode(
            new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(filter))
        );

        // * Put a +1/+1 counter on each creature you control.
        this.getSpellAbility().addMode(
            new Mode(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                StaticFilters.FILTER_CONTROLLED_CREATURE
            ))
        );
    }

    private MethodsOfTheMighty(final MethodsOfTheMighty card) {
        super(card);
    }

    @Override
    public MethodsOfTheMighty copy() {
        return new MethodsOfTheMighty(this);
    }
}

package mage.cards.h;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.RemoveUpToAmountCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeartlessAct extends CardImpl {

    private static final FilterCreaturePermanent filterWithoutCounters
            = new FilterCreaturePermanent("creature with no counters on it");

    static {
        filterWithoutCounters.add(Predicates.not(CounterAnyPredicate.instance));
    }

    public HeartlessAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose one —
        // • Destroy target creature with no counters on it.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filterWithoutCounters));

        // • Remove up to three counters from target creature.
        Mode mode = new Mode(new RemoveUpToAmountCountersEffect(3));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private HeartlessAct(final HeartlessAct card) {
        super(card);
    }

    @Override
    public HeartlessAct copy() {
        return new HeartlessAct(this);
    }
}
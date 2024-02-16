package mage.cards.d;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DamningVerdict extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creatures with no counters on them");

    static {
        filter.add(Predicates.not(CounterAnyPredicate.instance));
    }

    public DamningVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Destroy all creatures with no counters on them.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private DamningVerdict(final DamningVerdict card) {
        super(card);
    }

    @Override
    public DamningVerdict copy() {
        return new DamningVerdict(this);
    }
}

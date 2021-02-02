
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author jeffwadsworth
 */
public final class Hindervines extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with no +1/+1 counters on them");
    
    static {
        filter.add(Predicates.not(CounterType.P1P1.getPredicate()));
    }

    public Hindervines(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");


        // Prevent all combat damage that would be dealt this turn by creatures with no +1/+1 counters on them.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true));
    }

    private Hindervines(final Hindervines card) {
        super(card);
    }

    @Override
    public Hindervines copy() {
        return new Hindervines(this);
    }
}

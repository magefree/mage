package mage.cards.i;

import java.util.UUID;

import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class InvoluntaryCooldown extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and/or creatures");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public InvoluntaryCooldown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Tap up to two target artifacts and/or creatures. Put two stun counters on each of them.
        this.getSpellAbility().addTarget(new TargetPermanent(0, 2, filter));
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(2))
                .setText("put two stun counters on each of them"));
    }

    private InvoluntaryCooldown(final InvoluntaryCooldown card) {
        super(card);
    }

    @Override
    public InvoluntaryCooldown copy() {
        return new InvoluntaryCooldown(this);
    }
}

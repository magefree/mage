package mage.cards.r;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadiantStrike extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or tapped creature");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                Predicates.and(
                        TappedPredicate.TAPPED,
                        CardType.CREATURE.getPredicate()
                )
        ));
    }

    public RadiantStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Destroy target artifact or tapped creature. You gain 3 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
    }

    private RadiantStrike(final RadiantStrike card) {
        super(card);
    }

    @Override
    public RadiantStrike copy() {
        return new RadiantStrike(this);
    }
}

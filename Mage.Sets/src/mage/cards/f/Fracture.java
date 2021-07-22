package mage.cards.f;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Fracture extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, enchantment, or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public Fracture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        // Destroy target artifact, enchantment, or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private Fracture(final Fracture card) {
        super(card);
    }

    @Override
    public Fracture copy() {
        return new Fracture(this);
    }
}

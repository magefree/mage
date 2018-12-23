package mage.cards.b;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Bedevil extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or planeswalker");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)
        ));
    }

    public Bedevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}{R}");

        // Destroy target artifact, creature, or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public Bedevil(final Bedevil card) {
        super(card);
    }

    @Override
    public Bedevil copy() {
        return new Bedevil(this);
    }
}

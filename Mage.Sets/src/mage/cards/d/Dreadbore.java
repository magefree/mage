
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 * @author magenoxx_at_gmail.com
 */
public final class Dreadbore extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or planeswalker");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)
        ));
    }

    public Dreadbore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{R}");


        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public Dreadbore(final Dreadbore card) {
        super(card);
    }

    @Override
    public Dreadbore copy() {
        return new Dreadbore(this);
    }
}

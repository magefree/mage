
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author Plopman
 */
public final class Purify extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and enchantments");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public Purify(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{W}");


        // Destroy all artifacts and enchantments.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public Purify(final Purify card) {
        super(card);
    }

    @Override
    public Purify copy() {
        return new Purify(this);
    }
}

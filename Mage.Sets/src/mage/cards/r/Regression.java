
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.ShuffleIntoLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author Styxo
 */
public final class Regression extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or enchantment");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public Regression(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Choose target artifact or enchantment. Its owner shuffles it into their library.
        this.getSpellAbility().addEffect(new ShuffleIntoLibraryTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public Regression(final Regression card) {
        super(card);
    }

    @Override
    public Regression copy() {
        return new Regression(this);
    }
}

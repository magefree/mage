
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author nigelzor
 */
public final class TemporalEddy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or land");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }

    public TemporalEddy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");

        // Put target creature or land on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    public TemporalEddy(final TemporalEddy card) {
        super(card);
    }

    @Override
    public TemporalEddy copy() {
        return new TemporalEddy(this);
    }
}

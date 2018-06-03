
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public final class TributeToTheWild extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an artifact or enchantment");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public TributeToTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Each opponent sacrifices an artifact or enchantment.
      this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));
    }

    public TributeToTheWild(final TributeToTheWild card) {
        super(card);
    }

    @Override
    public TributeToTheWild copy() {
        return new TributeToTheWild(this);
    }
}

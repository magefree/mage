
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class TributeToTheWild extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an artifact or enchantment");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public TributeToTheWild(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Each opponent sacrifices an artifact or enchantment.
      this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));
    }

    private TributeToTheWild(final TributeToTheWild card) {
        super(card);
    }

    @Override
    public TributeToTheWild copy() {
        return new TributeToTheWild(this);
    }
}

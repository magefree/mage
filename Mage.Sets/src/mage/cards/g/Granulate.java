
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;

/**
 *
 * @author Plopman
 */
public final class Granulate extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland artifact with converted mana cost 4 or less");
    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 5));
    }
    public Granulate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");


        // Destroy each nonland artifact with converted mana cost 4 or less.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public Granulate(final Granulate card) {
        super(card);
    }

    @Override
    public Granulate copy() {
        return new Granulate(this);
    }
}

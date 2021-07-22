
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author Plopman
 */
public final class Granulate extends CardImpl {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent();
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }
    public Granulate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");


        // Destroy each nonland artifact with converted mana cost 4 or less.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter).setText("destroy each nonland artifact with mana value 4 or less"));
    }

    private Granulate(final Granulate card) {
        super(card);
    }

    @Override
    public Granulate copy() {
        return new Granulate(this);
    }
}

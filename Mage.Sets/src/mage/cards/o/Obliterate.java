
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.CantBeCounteredAbility;
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
public final class Obliterate extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and lands");
    
    static{
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }
    
    public Obliterate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{R}{R}");


        // Obliterate can't be countered.
        this.addAbility(new CantBeCounteredAbility());
        // Destroy all artifacts, creatures, and lands. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    public Obliterate(final Obliterate card) {
        super(card);
    }

    @Override
    public Obliterate copy() {
        return new Obliterate(this);
    }
}

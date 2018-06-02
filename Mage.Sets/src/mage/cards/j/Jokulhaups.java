
package mage.cards.j;

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
 * @author Quercitron
 */
public final class Jokulhaups extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifacts, creatures, and lands");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.LAND)));
    }
    
    public Jokulhaups(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{R}");


        // Destroy all artifacts, creatures, and lands. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, true));
    }

    public Jokulhaups(final Jokulhaups card) {
        super(card);
    }

    @Override
    public Jokulhaups copy() {
        return new Jokulhaups(this);
    }
}

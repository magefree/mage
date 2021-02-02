
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class Perish extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("green creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
        filter.add(CardType.CREATURE.getPredicate());
    }

    public Perish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // Destroy all green creatures. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, true));
    }

    private Perish(final Perish card) {
        super(card);
    }

    @Override
    public Perish copy() {
        return new Perish(this);
    }
}

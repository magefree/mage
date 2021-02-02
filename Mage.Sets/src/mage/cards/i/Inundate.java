
package mage.cards.i;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class Inundate extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblue creatures");
    
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLUE)));
    }

    public Inundate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}{U}{U}");


        // Return all nonblue creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(filter));
        
    }

    private Inundate(final Inundate card) {
        super(card);
    }

    @Override
    public Inundate copy() {
        return new Inundate(this);
    }
}

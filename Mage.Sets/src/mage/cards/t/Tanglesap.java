
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class Tanglesap extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures without trample");
    
    static {
        filter.add(Predicates.not(new AbilityPredicate(TrampleAbility.class)));
    }

    public Tanglesap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");


        // Prevent all combat damage that would be dealt this turn by creatures without trample.
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(filter, Duration.EndOfTurn, true));
        
    }

    private Tanglesap(final Tanglesap card) {
        super(card);
    }

    @Override
    public Tanglesap copy() {
        return new Tanglesap(this);
    }
}

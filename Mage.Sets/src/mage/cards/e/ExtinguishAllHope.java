
package mage.cards.e;

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
 * @author LevelX2
 */
public final class ExtinguishAllHope extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nonenchantment creatures");
    
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.not(new CardTypePredicate(CardType.ENCHANTMENT)));
    }
    
    
    public ExtinguishAllHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        // Destroy all nonenchantment creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
        
    }

    public ExtinguishAllHope(final ExtinguishAllHope card) {
        super(card);
    }

    @Override
    public ExtinguishAllHope copy() {
        return new ExtinguishAllHope(this);
    }
}

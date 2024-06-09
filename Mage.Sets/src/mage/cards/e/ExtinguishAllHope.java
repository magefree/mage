
package mage.cards.e;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ExtinguishAllHope extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonenchantment creatures");
    
    static {
        filter.add(Predicates.not(CardType.ENCHANTMENT.getPredicate()));
    }
    
    
    public ExtinguishAllHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        // Destroy all nonenchantment creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
        
    }

    private ExtinguishAllHope(final ExtinguishAllHope card) {
        super(card);
    }

    @Override
    public ExtinguishAllHope copy() {
        return new ExtinguishAllHope(this);
    }
}

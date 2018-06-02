
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public final class ManaMatrix extends CardImpl {

    private static final FilterCard filter = new FilterCard("Instant and enchantment spells");
    
    static {
        filter.add(Predicates.or(
            new CardTypePredicate(CardType.INSTANT),
            new CardTypePredicate(CardType.ENCHANTMENT)
        ));                
    }
            
    public ManaMatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // Instant and enchantment spells you cast cost up to {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 2, true)));        
    }

    public ManaMatrix(final ManaMatrix card) {
        super(card);
    }

    @Override
    public ManaMatrix copy() {
        return new ManaMatrix(this);
    }
}


package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;

/**
 *
 * @author jeffwadsworth

 */
public final class GreaterAuramancy extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("enchantments");
    private static final FilterPermanent filter2 = new FilterPermanent("Enchanted creatures");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
        filter2.add(new ControllerPredicate(TargetController.YOU));
        filter2.add(new CardTypePredicate(CardType.CREATURE));
        filter2.add(new EnchantedPredicate());
    }

    public GreaterAuramancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Other enchantments you control have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));
        
        // Enchanted creatures you control have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, filter2)));
        
    }

    public GreaterAuramancy(final GreaterAuramancy card) {
        super(card);
    }

    @Override
    public GreaterAuramancy copy() {
        return new GreaterAuramancy(this);
    }
}


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
import mage.filter.predicate.permanent.EnchantedPredicate;

/**
 *
 * @author jeffwadsworth

 */
public final class GreaterAuramancy extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("enchantments");
    private static final FilterPermanent filter2 = new FilterPermanent("Enchanted creatures");
    
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CardType.ENCHANTMENT.getPredicate());
        filter2.add(TargetController.YOU.getControllerPredicate());
        filter2.add(CardType.CREATURE.getPredicate());
        filter2.add(EnchantedPredicate.instance);
    }

    public GreaterAuramancy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Other enchantments you control have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));
        
        // Enchanted creatures you control have shroud.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(ShroudAbility.getInstance(), Duration.WhileOnBattlefield, filter2)));
        
    }

    private GreaterAuramancy(final GreaterAuramancy card) {
        super(card);
    }

    @Override
    public GreaterAuramancy copy() {
        return new GreaterAuramancy(this);
    }
}

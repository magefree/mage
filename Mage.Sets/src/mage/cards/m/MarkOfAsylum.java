
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllNonCombatDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreatureInPlay;

/**
 *
 * @author jeffwadsworth
 */
public final class MarkOfAsylum extends CardImpl {
    
    private static final FilterControlledCreatureInPlay filter = new FilterControlledCreatureInPlay("creatures you control");
    
    public MarkOfAsylum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");


        // Prevent all noncombat damage that would be dealt to creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllNonCombatDamageToAllEffect(Duration.WhileOnBattlefield, filter)));
        
    }

    public MarkOfAsylum(final MarkOfAsylum card) {
        super(card);
    }

    @Override
    public MarkOfAsylum copy() {
        return new MarkOfAsylum(this);
    }
}

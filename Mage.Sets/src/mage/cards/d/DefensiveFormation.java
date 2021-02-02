
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.ControllerAssignCombatDamageToBlockersAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author L_J
 */
public final class DefensiveFormation extends CardImpl {

    public DefensiveFormation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        
        // Rather than the attacking player, you assign the combat damage of each creature attacking you. You can divide that creature's combat damage as you choose among any of the creatures blocking it.
        this.addAbility(ControllerAssignCombatDamageToBlockersAbility.getInstance());
    }

    private DefensiveFormation(final DefensiveFormation card) {
        super(card);
    }

    @Override
    public DefensiveFormation copy() {
        return new DefensiveFormation(this);
    }
}

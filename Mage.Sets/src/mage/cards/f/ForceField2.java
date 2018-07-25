
package mage.cards.f;

import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;

import java.util.UUID;

/**
 * Rethemed Aetherize
 *
 * @author NinthWorld
 */
public final class ForceField2 extends CardImpl {

    public ForceField2(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Return all attacking creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(new FilterAttackingCreature("attacking creatures")));
    }

    public ForceField2(final ForceField2 card) {
        super(card);
    }

    @Override
    public ForceField2 copy() {
        return new ForceField2(this);
    }
}


package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author LevelX2
 */
public final class Aetherize extends CardImpl {

    public Aetherize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Return all attacking creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandFromBattlefieldAllEffect(new FilterAttackingCreature("attacking creatures")));
    }

    public Aetherize(final Aetherize card) {
        super(card);
    }

    @Override
    public Aetherize copy() {
        return new Aetherize(this);
    }
}

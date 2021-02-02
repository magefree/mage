
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author Loki
 */
public final class Megrim extends CardImpl {

    public Megrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        // Whenever an opponent discards a card, Megrim deals 2 damage to that player.
        this.addAbility(new DiscardsACardOpponentTriggeredAbility(new DamageTargetEffect(2, true, "that player"), false, SetTargetPointer.PLAYER));
    }

    private Megrim(final Megrim card) {
        super(card);
    }

    @Override
    public Megrim copy() {
        return new Megrim(this);
    }

}


package mage.cards.u;

import java.util.UUID;
import mage.abilities.common.DrawCardOpponentTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class UnderworldDreams extends CardImpl {

    public UnderworldDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{B}{B}");

        // Whenever an opponent draws a card, Underworld Dreams deals 1 damage to him or her.
        this.addAbility(new DrawCardOpponentTriggeredAbility(new DamageTargetEffect(1, true, "that player"), false, true));
    }

    public UnderworldDreams(final UnderworldDreams card) {
        super(card);
    }

    @Override
    public UnderworldDreams copy() {
        return new UnderworldDreams(this);
    }
}

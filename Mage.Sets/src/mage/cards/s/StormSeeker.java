
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.CardsInTargetHandCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author hanasu
 */
public final class StormSeeker extends CardImpl {

    public StormSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        // Storm Seeker deals damage to target player equal to the number of cards in that player's hand.
        Effect effect = new DamageTargetEffect(CardsInTargetHandCount.instance);
        effect.setText("{this} deals damage to target player equal to the number of cards in that player's hand.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private StormSeeker(final StormSeeker card) {
        super(card);
    }

    @Override
    public StormSeeker copy() {
        return new StormSeeker(this);
    }
}

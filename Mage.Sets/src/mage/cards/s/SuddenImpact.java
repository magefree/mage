
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
 * @author emerald000
 */
public final class SuddenImpact extends CardImpl {

    public SuddenImpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");


        // Sudden Impact deals damage to target player equal to the number of cards in that player's hand.
        Effect effect = new DamageTargetEffect(CardsInTargetHandCount.instance);
        effect.setText("{this} deals damage to target player equal to the number of cards in that player's hand.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SuddenImpact(final SuddenImpact card) {
        super(card);
    }

    @Override
    public SuddenImpact copy() {
        return new SuddenImpact(this);
    }
}

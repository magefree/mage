
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author jeffwadsworth
 */
public final class BreathOfMalfegor extends CardImpl {

    public BreathOfMalfegor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}{R}");


        

        // Breath of Malfegor deals 5 damage to each opponent.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(5, TargetController.OPPONENT));
        
    }

    private BreathOfMalfegor(final BreathOfMalfegor card) {
        super(card);
    }

    @Override
    public BreathOfMalfegor copy() {
        return new BreathOfMalfegor(this);
    }
}

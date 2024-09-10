

package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.InsectInfectToken;

/**
 *
 * @author Loki
 */
public final class CarrionCall extends CardImpl {

    public CarrionCall (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{G}");

        // Create two 1/1 green Insect creature tokens with lifelink. (They deal damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        this.getSpellAbility().addEffect(new CreateTokenEffect(new InsectInfectToken(), 2));
    }

    private CarrionCall(final CarrionCall card) {
        super(card);
    }

    @Override
    public CarrionCall copy() {
        return new CarrionCall(this);
    }
}

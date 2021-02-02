
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author anonymous
 */
public final class MarshGas extends CardImpl {

    public MarshGas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // All creatures get -2/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, 0, Duration.EndOfTurn));
    }

    private MarshGas(final MarshGas card) {
        super(card);
    }

    @Override
    public MarshGas copy() {
        return new MarshGas(this);
    }
}

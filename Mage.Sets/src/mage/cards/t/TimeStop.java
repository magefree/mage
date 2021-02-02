
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.EndTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jeff
 */
public final class TimeStop extends CardImpl {

    public TimeStop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}{U}");


        // End the turn.
        this.getSpellAbility().addEffect(new EndTurnEffect());
    }

    private TimeStop(final TimeStop card) {
        super(card);
    }

    @Override
    public TimeStop copy() {
        return new TimeStop(this);
    }
}

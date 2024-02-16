

package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Loki
 */
public final class TurnTheTide extends CardImpl {

    public TurnTheTide (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        this.getSpellAbility().addEffect(new BoostOpponentsEffect(-2, 0, Duration.EndOfTurn));
    }

    private TurnTheTide(final TurnTheTide card) {
        super(card);
    }

    @Override
    public TurnTheTide copy() {
        return new TurnTheTide(this);
    }

}

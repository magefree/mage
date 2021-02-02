
package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Plopman
 */
public final class Nausea extends CardImpl {

    public Nausea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");


        // All creatures get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-1, -1, Duration.EndOfTurn));
    }

    private Nausea(final Nausea card) {
        super(card);
    }

    @Override
    public Nausea copy() {
        return new Nausea(this);
    }
}

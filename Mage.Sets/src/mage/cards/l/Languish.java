
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class Languish extends CardImpl {

    public Languish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{B}");

        // All creatures get -4/-4 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-4, -4, Duration.EndOfTurn));
    }

    private Languish(final Languish card) {
        super(card);
    }

    @Override
    public Languish copy() {
        return new Languish(this);
    }
}

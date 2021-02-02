
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class Steadfastness extends CardImpl {

    public Steadfastness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Creatures you control get +0/+3 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(0, 3, Duration.EndOfTurn));
    }

    private Steadfastness(final Steadfastness card) {
        super(card);
    }

    @Override
    public Steadfastness copy() {
        return new Steadfastness(this);
    }
}

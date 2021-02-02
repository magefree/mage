
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author North
 */
public final class BarTheDoor extends CardImpl {

    public BarTheDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Creatures you control get +0/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(0, 4, Duration.EndOfTurn));
    }

    private BarTheDoor(final BarTheDoor card) {
        super(card);
    }

    @Override
    public BarTheDoor copy() {
        return new BarTheDoor(this);
    }
}


package mage.cards.w;

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
public final class WarriorsCharge extends CardImpl {

    public WarriorsCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
    }

    private WarriorsCharge(final WarriorsCharge card) {
        super(card);
    }

    @Override
    public WarriorsCharge copy() {
        return new WarriorsCharge(this);
    }
}


package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LoneFox
 */
public final class DesperateCharge extends CardImpl {

    public DesperateCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));
    }

    private DesperateCharge(final DesperateCharge card) {
        super(card);
    }

    @Override
    public DesperateCharge copy() {
        return new DesperateCharge(this);
    }
}

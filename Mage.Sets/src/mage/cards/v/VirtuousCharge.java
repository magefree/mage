
package mage.cards.v;

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
public final class VirtuousCharge extends CardImpl {

    public VirtuousCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Creatures you control get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn));
    }

    private VirtuousCharge(final VirtuousCharge card) {
        super(card);
    }

    @Override
    public VirtuousCharge copy() {
        return new VirtuousCharge(this);
    }
}

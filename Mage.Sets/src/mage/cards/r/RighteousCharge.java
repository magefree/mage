
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class RighteousCharge extends CardImpl {

    public RighteousCharge (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}{W}");


        // Creatures you control get +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2,2, Duration.EndOfTurn));
    }

    private RighteousCharge(final RighteousCharge card) {
        super(card);
    }

    @Override
    public RighteousCharge  copy() {
        return new RighteousCharge(this);
    }
}

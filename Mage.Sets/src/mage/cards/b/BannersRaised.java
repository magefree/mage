
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Loki
 */
public final class BannersRaised extends CardImpl {

    public BannersRaised(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Creatures you control get +1/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 0, Duration.EndOfTurn));
    }

    private BannersRaised(final BannersRaised card) {
        super(card);
    }

    @Override
    public BannersRaised copy() {
        return new BannersRaised(this);
    }
}

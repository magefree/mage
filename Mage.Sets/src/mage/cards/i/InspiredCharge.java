

package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class InspiredCharge extends CardImpl {

    public InspiredCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{W}");

        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
    }

    private InspiredCharge(final InspiredCharge card) {
        super(card);
    }

    @Override
    public InspiredCharge copy() {
        return new InspiredCharge(this);
    }
}


package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class VitalizingWind extends CardImpl {

    public VitalizingWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{8}{G}");
        

        // Creatures you control get +7/+7 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(7, 7, Duration.EndOfTurn));
    }

    private VitalizingWind(final VitalizingWind card) {
        super(card);
    }

    @Override
    public VitalizingWind copy() {
        return new VitalizingWind(this);
    }
}

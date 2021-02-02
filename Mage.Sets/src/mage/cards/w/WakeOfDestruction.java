
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllNamedPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Styxo
 */
public final class WakeOfDestruction extends CardImpl {

    public WakeOfDestruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}{R}");

        // Destroy target land and all other lands with the same name as that land.
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new DestroyAllNamedPermanentsEffect());
    }

    private WakeOfDestruction(final WakeOfDestruction card) {
        super(card);
    }

    @Override
    public WakeOfDestruction copy() {
        return new WakeOfDestruction(this);
    }
}

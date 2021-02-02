
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class VolcanicUpheaval extends CardImpl {

    public VolcanicUpheaval(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");

        // Destroy target land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());

    }

    private VolcanicUpheaval(final VolcanicUpheaval card) {
        super(card);
    }

    @Override
    public VolcanicUpheaval copy() {
        return new VolcanicUpheaval(this);
    }
}

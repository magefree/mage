
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author AlumiuN
 */
public final class Deathlace extends CardImpl {

    public Deathlace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Target spell or permanent becomes black.
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent());
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(ObjectColor.BLACK, Duration.Custom));
    }

    private Deathlace(final Deathlace card) {
        super(card);
    }

    @Override
    public Deathlace copy() {
        return new Deathlace(this);
    }
}

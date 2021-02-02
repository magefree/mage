
package mage.cards.p;

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
public final class Purelace extends CardImpl {

    public Purelace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target spell or permanent becomes white.
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent());
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(ObjectColor.WHITE, Duration.Custom));
    }

    private Purelace(final Purelace card) {
        super(card);
    }

    @Override
    public Purelace copy() {
        return new Purelace(this);
    }
}

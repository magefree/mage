
package mage.cards.m;

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
public final class Moonlace extends CardImpl {

    public Moonlace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Target spell or permanent becomes colorless.
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent());
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(ObjectColor.COLORLESS, Duration.Custom));
    }

    private Moonlace(final Moonlace card) {
        super(card);
    }

    @Override
    public Moonlace copy() {
        return new Moonlace(this);
    }
}

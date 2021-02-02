
package mage.cards.t;

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
public final class Thoughtlace extends CardImpl {

    public Thoughtlace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Target spell or permanent becomes blue.
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent());
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(ObjectColor.BLUE, Duration.Custom));
    }

    private Thoughtlace(final Thoughtlace card) {
        super(card);
    }

    @Override
    public Thoughtlace copy() {
        return new Thoughtlace(this);
    }
}

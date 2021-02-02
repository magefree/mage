
package mage.cards.l;

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
public final class Lifelace extends CardImpl {

    public Lifelace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target spell or permanent becomes green.
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent());
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(ObjectColor.GREEN, Duration.Custom));
    }

    private Lifelace(final Lifelace card) {
        super(card);
    }

    @Override
    public Lifelace copy() {
        return new Lifelace(this);
    }
}

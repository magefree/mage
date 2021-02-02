
package mage.cards.c;

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
public final class Chaoslace extends CardImpl {

    public Chaoslace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Target spell or permanent becomes red.
        this.getSpellAbility().addTarget(new TargetSpellOrPermanent());
        this.getSpellAbility().addEffect(new BecomesColorTargetEffect(ObjectColor.RED, Duration.Custom));
    }

    private Chaoslace(final Chaoslace card) {
        super(card);
    }

    @Override
    public Chaoslace copy() {
        return new Chaoslace(this);
    }
}
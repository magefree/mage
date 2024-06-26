
package mage.cards.d;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DistortingWake extends CardImpl {

    public DistortingWake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}{U}");

        // Return X target nonland permanents to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target nonland permanents to their owners' hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
    }

    private DistortingWake(final DistortingWake card) {
        super(card);
    }

    @Override
    public DistortingWake copy() {
        return new DistortingWake(this);
    }
}

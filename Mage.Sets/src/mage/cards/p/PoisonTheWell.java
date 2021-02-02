
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class PoisonTheWell extends CardImpl {

    public PoisonTheWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B/R}{B/R}");

        // Destroy target land. Poison the Well deals 2 damage to that land's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Effect effect = new DamageTargetControllerEffect(2);
        effect.setText("{this} deals 2 damage to that land's controller");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private PoisonTheWell(final PoisonTheWell card) {
        super(card);
    }

    @Override
    public PoisonTheWell copy() {
        return new PoisonTheWell(this);
    }
}

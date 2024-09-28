
package mage.cards.p;

import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class PoisonTheWell extends CardImpl {

    public PoisonTheWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B/R}{B/R}");

        // Destroy target land. Poison the Well deals 2 damage to that land's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DamageTargetControllerEffect(2, "land"));
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

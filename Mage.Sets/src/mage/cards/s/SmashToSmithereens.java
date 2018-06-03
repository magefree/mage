
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author jonubuu
 */
public final class SmashToSmithereens extends CardImpl {

    public SmashToSmithereens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Destroy target artifact. Smash to Smithereens deals 3 damage to that artifact's controller.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        Effect effect = new DamageTargetControllerEffect(3);
        effect.setText("{this} deals 3 damage to that artifact's controller");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
    }

    public SmashToSmithereens(final SmashToSmithereens card) {
        super(card);
    }

    @Override
    public SmashToSmithereens copy() {
        return new SmashToSmithereens(this);
    }
}

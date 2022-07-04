
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author L_J
 */
public final class RhysticLightning extends CardImpl {

    public RhysticLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Rhystic Lightning deals 4 damage to any target unless that creature's controller or that player pays {2}. If they do, Rhystic Lightning deals 2 damage to the creature or player.
        Effect effect = new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new DamageTargetEffect(4), new DamageTargetEffect(2), new ManaCostsImpl<>("{2}"), 
            "Pay {2} to have {this} deal 2 damage instead of 4 damage?");
        effect.setText("{this} deals 4 damage to any target unless that creature's controller or that player pays {2}. If they do, {this} deals 2 damage to the creature or player");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private RhysticLightning(final RhysticLightning card) {
        super(card);
    }

    @Override
    public RhysticLightning copy() {
        return new RhysticLightning(this);
    }
}

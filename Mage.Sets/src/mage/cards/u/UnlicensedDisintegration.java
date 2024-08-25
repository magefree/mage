
package mage.cards.u;

import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetControllerEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author emerald000
 */
public final class UnlicensedDisintegration extends CardImpl {

    public UnlicensedDisintegration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}{R}");

        // Destroy target creature. If you control an artifact, Unlicensed Disintegration deals 3 damage to that creature's controller.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetControllerEffect(3),
                new YouControlPermanentCondition(new FilterArtifactPermanent())));
        
    }

    private UnlicensedDisintegration(final UnlicensedDisintegration card) {
        super(card);
    }

    @Override
    public UnlicensedDisintegration copy() {
        return new UnlicensedDisintegration(this);
    }
}

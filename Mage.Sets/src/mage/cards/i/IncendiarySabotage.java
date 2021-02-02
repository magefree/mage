
package mage.cards.i;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author cg5
 */
public final class IncendiarySabotage extends CardImpl {

    public IncendiarySabotage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}{R}");

        // As an additional cost to cast Incendiary Sabotage, sacrifice an artifact.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent("an artifact"))));
        
        // Incendiary Sabotage deals 3 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(3, new FilterCreaturePermanent()));
    }

    private IncendiarySabotage(final IncendiarySabotage card) {
        super(card);
    }

    @Override
    public IncendiarySabotage copy() {
        return new IncendiarySabotage(this);
    }
}

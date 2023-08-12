
package mage.cards.c;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class CollateralDamage extends CardImpl {

    public CollateralDamage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // As an additional cost to cast Collateral Damge, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // Collateral Damage deals 3 damage to any target.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
    }

    private CollateralDamage(final CollateralDamage card) {
        super(card);
    }

    @Override
    public CollateralDamage copy() {
        return new CollateralDamage(this);
    }
}

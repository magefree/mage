
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetEnchantmentPermanent;

/**
 *
 * @author fireshoes
 */
public final class AuraFracture extends CardImpl {

    public AuraFracture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // Sacrifice a land: Destroy target enchantment.
        Ability ability = new SimpleActivatedAbility(
                new DestroyTargetEffect(), 
                new SacrificeTargetCost(StaticFilters.FILTER_LAND));
        ability.addTarget(new TargetEnchantmentPermanent());
        this.addAbility(ability);
    }

    private AuraFracture(final AuraFracture card) {
        super(card);
    }

    @Override
    public AuraFracture copy() {
        return new AuraFracture(this);
    }
}

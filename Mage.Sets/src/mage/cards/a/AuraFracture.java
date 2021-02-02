
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
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
                Zone.BATTLEFIELD, 
                new DestroyTargetEffect(), 
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("land"))));
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

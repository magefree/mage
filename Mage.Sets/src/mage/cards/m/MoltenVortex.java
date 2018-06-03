
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class MoltenVortex extends CardImpl {

    public MoltenVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");

        // {R}, Discard a land card: Molten Vortex deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new DiscardTargetCost(new TargetCardInHand(new FilterLandCard())));
        ability.addCost(new ManaCostsImpl("{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public MoltenVortex(final MoltenVortex card) {
        super(card);
    }

    @Override
    public MoltenVortex copy() {
        return new MoltenVortex(this);
    }
}


package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MoltenVortex extends CardImpl {

    public MoltenVortex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // {R}, Discard a land card: Molten Vortex deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(
                new DamageTargetEffect(2), new ManaCostsImpl<>("{R}")
        );
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(StaticFilters.FILTER_CARD_LAND_A)));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private MoltenVortex(final MoltenVortex card) {
        super(card);
    }

    @Override
    public MoltenVortex copy() {
        return new MoltenVortex(this);
    }
}

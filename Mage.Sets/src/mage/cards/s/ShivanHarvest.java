
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class ShivanHarvest extends CardImpl {

    public ShivanHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // {1}{R}, Sacrifice a creature: Destroy target nonbasic land.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        ability.addTarget(new TargetNonBasicLandPermanent());
        this.addAbility(ability);
    }

    private ShivanHarvest(final ShivanHarvest card) {
        super(card);
    }

    @Override
    public ShivanHarvest copy() {
        return new ShivanHarvest(this);
    }
}

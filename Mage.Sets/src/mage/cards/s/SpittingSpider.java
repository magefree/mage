
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SpittingSpider extends CardImpl {

    public SpittingSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Sacrifice a land: Spitting Spider deals 1 damage to each creature with flying.
        this.addAbility(new SimpleActivatedAbility(new DamageAllEffect(1, StaticFilters.FILTER_CREATURE_FLYING), new SacrificeTargetCost(StaticFilters.FILTER_LAND)));
    }

    private SpittingSpider(final SpittingSpider card) {
        super(card);
    }

    @Override
    public SpittingSpider copy() {
        return new SpittingSpider(this);
    }
}

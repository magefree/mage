
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class BlightedSteppe extends CardImpl {

    public BlightedSteppe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}{W}, {T}, Sacrifice Blighted Steppe: You gain 2 life for each creature you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainLifeEffect(new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED, 2)),
                new ManaCostsImpl<>("{3}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BlightedSteppe(final BlightedSteppe card) {
        super(card);
    }

    @Override
    public BlightedSteppe copy() {
        return new BlightedSteppe(this);
    }
}

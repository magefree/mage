
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class BlightedFen extends CardImpl {

    public BlightedFen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}{B}, {T}, Sacrifice Blighted Fen: Target opponent sacrifices a creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target opponent"),
                new ManaCostsImpl<>("{4}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BlightedFen(final BlightedFen card) {
        super(card);
    }

    @Override
    public BlightedFen copy() {
        return new BlightedFen(this);
    }
}

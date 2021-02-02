
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class PerilousVault extends CardImpl {

    public PerilousVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {5}, {T}, Exile Perilous Vault: Exile all nonland permanents.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND),
                new GenericManaCost(5)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private PerilousVault(final PerilousVault card) {
        super(card);
    }

    @Override
    public PerilousVault copy() {
        return new PerilousVault(this);
    }
}

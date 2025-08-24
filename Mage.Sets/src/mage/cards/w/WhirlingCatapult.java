
package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromTopOfLibraryCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author L_J
 */
public final class WhirlingCatapult extends CardImpl {

    public WhirlingCatapult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {2}, Exile the top two cards of your library: Whirling Catapult deals 1 damage to each creature with flying and each player.
        Ability ability = new SimpleActivatedAbility(new DamageEverythingEffect(1, StaticFilters.FILTER_CREATURE_FLYING), new ManaCostsImpl<>("{2}"));
        ability.addCost(new ExileFromTopOfLibraryCost(2));
        this.addAbility(ability);
    }

    private WhirlingCatapult(final WhirlingCatapult card) {
        super(card);
    }

    @Override
    public WhirlingCatapult copy() {
        return new WhirlingCatapult(this);
    }
}

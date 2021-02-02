
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Galatolol
 */
public final class StandingStones extends CardImpl {

    public StandingStones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        

        // {1}, {tap}, Pay 1 life: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new GenericManaCost(1));
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability);
    }

    private StandingStones(final StandingStones card) {
        super(card);
    }

    @Override
    public StandingStones copy() {
        return new StandingStones(this);
    }
}

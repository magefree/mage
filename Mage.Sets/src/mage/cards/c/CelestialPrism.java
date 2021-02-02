
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author icetc
 */
public final class CelestialPrism extends CardImpl {

    public CelestialPrism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {2}, {tap}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CelestialPrism(final CelestialPrism card) {
        super(card);
    }

    @Override
    public CelestialPrism copy() {
        return new CelestialPrism(this);
    }
}

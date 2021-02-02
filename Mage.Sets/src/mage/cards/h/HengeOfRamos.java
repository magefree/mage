
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class HengeOfRamos extends CardImpl {

    public HengeOfRamos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}, {tap}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HengeOfRamos(final HengeOfRamos card) {
        super(card);
    }

    @Override
    public HengeOfRamos copy() {
        return new HengeOfRamos(this);
    }
}

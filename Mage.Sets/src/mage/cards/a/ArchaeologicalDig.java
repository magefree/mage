
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class ArchaeologicalDig extends CardImpl {

    public ArchaeologicalDig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}, Sacrifice Archaeological Dig: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ArchaeologicalDig(final ArchaeologicalDig card) {
        super(card);
    }

    @Override
    public ArchaeologicalDig copy() {
        return new ArchaeologicalDig(this);
    }
}

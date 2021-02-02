
package mage.cards.w;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author anonymous
 */
public final class WizardsSchool extends CardImpl {

    public WizardsSchool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}, {tap}: Add {U}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {2}, {tap}: Add {W} or {B}.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WizardsSchool(final WizardsSchool card) {
        super(card);
    }

    @Override
    public WizardsSchool copy() {
        return new WizardsSchool(this);
    }
}


package mage.cards.a;

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
public final class AysenAbbey extends CardImpl {

    public AysenAbbey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}, {tap}: Add {W}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {2}, {tap}: Add {G} or {U}.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AysenAbbey(final AysenAbbey card) {
        super(card);
    }

    @Override
    public AysenAbbey copy() {
        return new AysenAbbey(this);
    }
}

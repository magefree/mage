
package mage.cards.c;

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
public final class CastleSengir extends CardImpl {

    public CastleSengir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}, {tap}: Add {B}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        // {2}, {tap}: Add {U} or {R}.
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CastleSengir(final CastleSengir card) {
        super(card);
    }

    @Override
    public CastleSengir copy() {
        return new CastleSengir(this);
    }
}


package mage.cards.d;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Quercitron
 */
public final class DwarvenRuins extends CardImpl {

    public DwarvenRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Dwarven Ruins enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());
        // {tap}, Sacrifice Dwarven Ruins: Add {R}{R}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.RedMana(2), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private DwarvenRuins(final DwarvenRuins card) {
        super(card);
    }

    @Override
    public DwarvenRuins copy() {
        return new DwarvenRuins(this);
    }
}

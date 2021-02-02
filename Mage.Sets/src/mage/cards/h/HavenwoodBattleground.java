
package mage.cards.h;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Quercitron
 */
public final class HavenwoodBattleground extends CardImpl {

    public HavenwoodBattleground(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Havenwood Battleground enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
        // {tap}, Sacrifice Havenwood Battleground: Add {G}{G}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private HavenwoodBattleground(final HavenwoodBattleground card) {
        super(card);
    }

    @Override
    public HavenwoodBattleground copy() {
        return new HavenwoodBattleground(this);
    }
}

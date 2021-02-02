
package mage.cards.e;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Quercitron
 */
public final class EbonStronghold extends CardImpl {

    public EbonStronghold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Ebon Stronghold enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
        // {tap}, Sacrifice Ebon Stronghold: Add {B}{B}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(2), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EbonStronghold(final EbonStronghold card) {
        super(card);
    }

    @Override
    public EbonStronghold copy() {
        return new EbonStronghold(this);
    }
}

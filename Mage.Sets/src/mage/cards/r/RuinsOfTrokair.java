
package mage.cards.r;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Quercitron
 */
public final class RuinsOfTrokair extends CardImpl {

    public RuinsOfTrokair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Ruins of Trokair enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
        // {tap}, Sacrifice Ruins of Trokair: Add {W}{W}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(2), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RuinsOfTrokair(final RuinsOfTrokair card) {
        super(card);
    }

    @Override
    public RuinsOfTrokair copy() {
        return new RuinsOfTrokair(this);
    }
}

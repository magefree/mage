
package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Quercitron
 */
public final class SvyeluniteTemple extends CardImpl {

    public SvyeluniteTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Svyelunite Temple enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {U}.
        this.addAbility(new BlueManaAbility());
        // {tap}, Sacrifice Svyelunite Temple: Add {U}{U}.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(2), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SvyeluniteTemple(final SvyeluniteTemple card) {
        super(card);
    }

    @Override
    public SvyeluniteTemple copy() {
        return new SvyeluniteTemple(this);
    }
}

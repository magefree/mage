

package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author cbt33
 */

public final class AbandonedOutpost extends CardImpl {

    public AbandonedOutpost(UUID ownerId, CardSetInfo setInfo){
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // This enters the battlefield tapped
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Tap to add {W}
        this.addAbility(new WhiteManaAbility());

        // Tap to add any color mana. Sacrifice Abandoned Outpost.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
		
    }
	
    private AbandonedOutpost(final AbandonedOutpost card) {
        super(card);
    }

    @Override
    public AbandonedOutpost copy() {
        return new AbandonedOutpost(this);
    }
	
}

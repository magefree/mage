
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.DredgeAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author North
 */
public final class DakmorSalvage extends CardImpl {

    public DakmorSalvage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Dakmor Salvage enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
        // Dredge 2
        this.addAbility(new DredgeAbility(2));
    }

    private DakmorSalvage(final DakmorSalvage card) {
        super(card);
    }

    @Override
    public DakmorSalvage copy() {
        return new DakmorSalvage(this);
    }
}

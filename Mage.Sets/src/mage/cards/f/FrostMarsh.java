
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

/**
 *
 * @author dustinconrad
 */
public final class FrostMarsh extends CardImpl {

    public FrostMarsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.SNOW);

        // Frost Marsh enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {U} or {B}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private FrostMarsh(final FrostMarsh card) {
        super(card);
    }

    @Override
    public FrostMarsh copy() {
        return new FrostMarsh(this);
    }
}

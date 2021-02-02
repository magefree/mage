
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Quercitron
 */
public final class FireDiamond extends CardImpl {

    public FireDiamond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Fire Diamond enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private FireDiamond(final FireDiamond card) {
        super(card);
    }

    @Override
    public FireDiamond copy() {
        return new FireDiamond(this);
    }
}

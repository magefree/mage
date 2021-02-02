
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Quercitron
 */
public final class SkyDiamond extends CardImpl {

    public SkyDiamond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Sky Diamond enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private SkyDiamond(final SkyDiamond card) {
        super(card);
    }

    @Override
    public SkyDiamond copy() {
        return new SkyDiamond(this);
    }
}

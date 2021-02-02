
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Quercitron
 */
public final class MarbleDiamond extends CardImpl {

    public MarbleDiamond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Marble Diamond enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private MarbleDiamond(final MarbleDiamond card) {
        super(card);
    }

    @Override
    public MarbleDiamond copy() {
        return new MarbleDiamond(this);
    }
}

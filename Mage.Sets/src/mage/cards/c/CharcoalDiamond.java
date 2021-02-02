
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Quercitron
 */
public final class CharcoalDiamond extends CardImpl {

    public CharcoalDiamond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Charcoal Diamond enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private CharcoalDiamond(final CharcoalDiamond card) {
        super(card);
    }

    @Override
    public CharcoalDiamond copy() {
        return new CharcoalDiamond(this);
    }
}

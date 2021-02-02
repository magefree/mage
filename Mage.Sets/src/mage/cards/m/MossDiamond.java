
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Quercitron
 */
public final class MossDiamond extends CardImpl {

    public MossDiamond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // Moss Diamond enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private MossDiamond(final MossDiamond card) {
        super(card);
    }

    @Override
    public MossDiamond copy() {
        return new MossDiamond(this);
    }
}

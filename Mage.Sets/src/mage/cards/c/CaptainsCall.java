
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author Loki
 */
public final class CaptainsCall extends CardImpl {

    public CaptainsCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}");

        // Create three 1/1 white Soldier creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoldierToken(), 3));
    }

    private CaptainsCall(final CaptainsCall card) {
        super(card);
    }

    @Override
    public CaptainsCall copy() {
        return new CaptainsCall(this);
    }
}

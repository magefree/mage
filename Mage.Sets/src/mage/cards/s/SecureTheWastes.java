
package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.WarriorToken;

/**
 *
 * @author fireshoes
 */
public final class SecureTheWastes extends CardImpl {

    public SecureTheWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{W}");

        // create X 1/1 white Warrior creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new WarriorToken(), GetXValue.instance));
    }

    private SecureTheWastes(final SecureTheWastes card) {
        super(card);
    }

    @Override
    public SecureTheWastes copy() {
        return new SecureTheWastes(this);
    }
}

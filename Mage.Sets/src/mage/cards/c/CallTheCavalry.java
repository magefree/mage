
package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KnightToken;

/**
 *
 * @author JRHerlehy
 */
public final class CallTheCavalry extends CardImpl {

    public CallTheCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Create two 2/2 white Knight creature tokens with vigilance.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KnightToken(), 2));
    }

    private CallTheCavalry(final CallTheCavalry card) {
        super(card);
    }

    @Override
    public CallTheCavalry copy() {
        return new CallTheCavalry(this);
    }
}

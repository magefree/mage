
package mage.cards.q;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.IxalanVampireToken;

/**
 *
 * @author TheElk801
 */
public final class QueensCommission extends CardImpl {

    public QueensCommission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // Create two 1/1 white Vampire creature tokens with lifelink.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new IxalanVampireToken(), 2));
    }

    private QueensCommission(final QueensCommission card) {
        super(card);
    }

    @Override
    public QueensCommission copy() {
        return new QueensCommission(this);
    }
}

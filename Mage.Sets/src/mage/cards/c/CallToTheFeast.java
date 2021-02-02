
package mage.cards.c;

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
public final class CallToTheFeast extends CardImpl {

    public CallToTheFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{B}");

        // Create three 1/1 white Vampire creature tokens with lifelink.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new IxalanVampireToken(), 3));
    }

    private CallToTheFeast(final CallToTheFeast card) {
        super(card);
    }

    @Override
    public CallToTheFeast copy() {
        return new CallToTheFeast(this);
    }
}

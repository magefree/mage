
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.CallTheSkyBreakerElementalToken;

/**
 *
 * @author jeffwadsworth
 */
public final class CallTheSkybreaker extends CardImpl {

    public CallTheSkybreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U/R}{U/R}");

        // Create a 5/5 blue and red Elemental creature token with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new CallTheSkyBreakerElementalToken()));

        // Retrace
        this.addAbility(new RetraceAbility(this));

    }

    private CallTheSkybreaker(final CallTheSkybreaker card) {
        super(card);
    }

    @Override
    public CallTheSkybreaker copy() {
        return new CallTheSkybreaker(this);
    }

}

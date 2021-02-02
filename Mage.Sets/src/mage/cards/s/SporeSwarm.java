
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author TheElk801
 */
public final class SporeSwarm extends CardImpl {

    public SporeSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Create three 1/1 green Saproling creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SaprolingToken(), 3));
    }

    private SporeSwarm(final SporeSwarm card) {
        super(card);
    }

    @Override
    public SporeSwarm copy() {
        return new SporeSwarm(this);
    }
}

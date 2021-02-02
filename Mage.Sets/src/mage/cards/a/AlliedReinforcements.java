
package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.KnightAllyToken;

/**
 *
 * @author fireshoes
 */
public final class AlliedReinforcements extends CardImpl {

    public AlliedReinforcements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Create two 2/2 white Knight Ally creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new KnightAllyToken(), 2));
    }

    private AlliedReinforcements(final AlliedReinforcements card) {
        super(card);
    }

    @Override
    public AlliedReinforcements copy() {
        return new AlliedReinforcements(this);
    }
}

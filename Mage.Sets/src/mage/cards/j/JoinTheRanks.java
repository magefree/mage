
package mage.cards.j;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.JoinTheRanksSoldierToken;

/**
 *
 * @author Loki
 */
public final class JoinTheRanks extends CardImpl {

    public JoinTheRanks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.color.setWhite(true);

        // Create two 1/1 white Soldier Ally creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new JoinTheRanksSoldierToken(), 2));
    }

    private JoinTheRanks(final JoinTheRanks card) {
        super(card);
    }

    @Override
    public JoinTheRanks copy() {
        return new JoinTheRanks(this);
    }

}

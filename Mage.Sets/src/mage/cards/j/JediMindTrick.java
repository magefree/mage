
package mage.cards.j;

import java.util.UUID;
import mage.abilities.effects.common.turn.ControlTargetPlayerNextTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class JediMindTrick extends CardImpl {

    public JediMindTrick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{U}");

        // You control target player during that player's next turn.
        this.getSpellAbility().addEffect(new ControlTargetPlayerNextTurnEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private JediMindTrick(final JediMindTrick card) {
        super(card);
    }

    @Override
    public JediMindTrick copy() {
        return new JediMindTrick(this);
    }
}

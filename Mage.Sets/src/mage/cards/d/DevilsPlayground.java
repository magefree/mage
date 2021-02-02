
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.DevilToken;

/**
 *
 * @author fireshoes
 */
public final class DevilsPlayground extends CardImpl {

    public DevilsPlayground(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // Create four 1/1 red Devil creature tokens. They have "When this creature dies, it deals 1 damage to any target."
        Effect effect = new CreateTokenEffect(new DevilToken(), 4);
        effect.setText("Create four 1/1 red Devil creature tokens. They have \"When this creature dies, it deals 1 damage to any target.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private DevilsPlayground(final DevilsPlayground card) {
        super(card);
    }

    @Override
    public DevilsPlayground copy() {
        return new DevilsPlayground(this);
    }
}

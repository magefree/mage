
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
public final class DanceWithDevils extends CardImpl {

    public DanceWithDevils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Create two 1/1 red Devil creature tokens. They have "When this creature dies, it deals 1 damage to any target."
        Effect effect = new CreateTokenEffect(new DevilToken(), 2);
        effect.setText("Create two 1/1 red Devil creature tokens. They have \"When this creature dies, it deals 1 damage to any target.\"");
        this.getSpellAbility().addEffect(effect);
    }

    private DanceWithDevils(final DanceWithDevils card) {
        super(card);
    }

    @Override
    public DanceWithDevils copy() {
        return new DanceWithDevils(this);
    }
}

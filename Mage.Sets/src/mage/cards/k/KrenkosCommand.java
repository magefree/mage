
package mage.cards.k;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author North
 */
public final class KrenkosCommand extends CardImpl {

    public KrenkosCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");


        // Create two 1/1 red Goblin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), 2));
    }

    private KrenkosCommand(final KrenkosCommand card) {
        super(card);
    }

    @Override
    public KrenkosCommand copy() {
        return new KrenkosCommand(this);
    }
}

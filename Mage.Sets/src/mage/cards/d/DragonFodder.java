
package mage.cards.d;

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
public final class DragonFodder extends CardImpl {

    public DragonFodder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Create two 1/1 red Goblin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), 2));
    }

    private DragonFodder(final DragonFodder card) {
        super(card);
    }

    @Override
    public DragonFodder copy() {
        return new DragonFodder(this);
    }
}

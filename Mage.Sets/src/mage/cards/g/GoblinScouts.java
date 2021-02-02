
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinScoutsToken;

/**
 *
 * @author fireshoes
 */
public final class GoblinScouts extends CardImpl {

    public GoblinScouts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Create three 1/1 red Goblin Scout creature tokens with mountainwalk.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinScoutsToken(), 3));
    }

    private GoblinScouts(final GoblinScouts card) {
        super(card);
    }

    @Override
    public GoblinScouts copy() {
        return new GoblinScouts(this);
    }
}

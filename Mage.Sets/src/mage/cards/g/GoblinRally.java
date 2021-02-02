

package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author LevelX2
 */
public final class GoblinRally extends CardImpl {

    public GoblinRally(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");


        // Create four 1/1 red Goblin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), 4));
    }

    private GoblinRally(final GoblinRally card) {
        super(card);
    }

    @Override
    public GoblinRally copy() {
        return new GoblinRally(this);
    }

}

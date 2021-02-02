
package mage.cards.h;

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
public final class HordelingOutburst extends CardImpl {

    public HordelingOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Put three 1/1 red Goblin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), 3));
    }

    private HordelingOutburst(final HordelingOutburst card) {
        super(card);
    }

    @Override
    public HordelingOutburst copy() {
        return new HordelingOutburst(this);
    }
}

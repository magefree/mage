
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author TheElk801
 */
public final class PiratesPrize extends CardImpl {

    public PiratesPrize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Draw two cards. Create a colorless Treasure artifact token with "{t}, Sacrifice this artifact: Add one mana of any color."
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
    }

    private PiratesPrize(final PiratesPrize card) {
        super(card);
    }

    @Override
    public PiratesPrize copy() {
        return new PiratesPrize(this);
    }
}

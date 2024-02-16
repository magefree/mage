
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.MinotaurToken;

/**
 *
 * @author LevelX2
 */
public final class FlurryOfHorns extends CardImpl {

    public FlurryOfHorns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Create two 2/3 red Minotaur creature tokens with haste.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new MinotaurToken(true), 2));
    }

    private FlurryOfHorns(final FlurryOfHorns card) {
        super(card);
    }

    @Override
    public FlurryOfHorns copy() {
        return new FlurryOfHorns(this);
    }
}

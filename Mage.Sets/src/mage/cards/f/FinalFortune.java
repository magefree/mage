
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Quercitron
 */
public final class FinalFortune extends CardImpl {

    public FinalFortune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}{R}");

        // Take an extra turn after this one. At the beginning of that turn's end step, you lose the game.
        this.getSpellAbility().addEffect(new AddExtraTurnControllerEffect(true));
    }

    private FinalFortune(final FinalFortune card) {
        super(card);
    }

    @Override
    public FinalFortune copy() {
        return new FinalFortune(this);
    }
}

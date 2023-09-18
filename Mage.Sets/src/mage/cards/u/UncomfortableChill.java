
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class UncomfortableChill extends CardImpl {

    public UncomfortableChill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Creatures your opponents control get -2/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostOpponentsEffect(-2, 0, Duration.EndOfTurn));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private UncomfortableChill(final UncomfortableChill card) {
        super(card);
    }

    @Override
    public UncomfortableChill copy() {
        return new UncomfortableChill(this);
    }
}

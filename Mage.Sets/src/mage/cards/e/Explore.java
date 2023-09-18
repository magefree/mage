
package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author Viserion
 */
public final class Explore extends CardImpl {

    public Explore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // You may play an additional land this turn.
        this.getSpellAbility().addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Explore(final Explore card) {
        super(card);
    }

    @Override
    public Explore copy() {
        return new Explore(this);
    }
}

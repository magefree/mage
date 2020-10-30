
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileCardFromOwnGraveyardControllerEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author L_J
 */
public final class FalseMemories extends CardImpl {

    public FalseMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Put the top seven cards of your library into your graveyard.
        this.getSpellAbility().addEffect(new MillCardsControllerEffect(7));
        // At the beginning of the next end step, exile seven cards from your graveyard.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ExileCardFromOwnGraveyardControllerEffect(7))));
    }

    public FalseMemories(final FalseMemories card) {
        super(card);
    }

    @Override
    public FalseMemories copy() {
        return new FalseMemories(this);
    }
}

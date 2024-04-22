package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.EndTurnEffect;
import mage.abilities.effects.common.LoseGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class GloriousEnd extends CardImpl {

    public GloriousEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // End the turn.
        getSpellAbility().addEffect(new EndTurnEffect());

        // At the beginning of your next end step, you lose the game.
        getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new LoseGameSourceControllerEffect(), TargetController.YOU))
                .concatBy("<br>"));
    }

    private GloriousEnd(final GloriousEnd card) {
        super(card);
    }

    @Override
    public GloriousEnd copy() {
        return new GloriousEnd(this);
    }
}

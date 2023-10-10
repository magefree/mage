
package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class RayOfErasure extends CardImpl {

    public RayOfErasure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Target player puts the top card of their library into their graveyard.
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse), false));
    }

    private RayOfErasure(final RayOfErasure card) {
        super(card);
    }

    @Override
    public RayOfErasure copy() {
        return new RayOfErasure(this);
    }
}


package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class Clairvoyance extends CardImpl {

    public Clairvoyance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Look at target player's hand.
        this.getSpellAbility().addEffect(new LookAtTargetPlayerHandEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        
        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse), false)
                .concatBy("<br>"));
    }

    private Clairvoyance(final Clairvoyance card) {
        super(card);
    }

    @Override
    public Clairvoyance copy() {
        return new Clairvoyance(this);
    }
}

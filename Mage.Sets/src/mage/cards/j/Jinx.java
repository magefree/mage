
package mage.cards.j;

import java.util.UUID;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class Jinx extends CardImpl {

    public Jinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Target land becomes the basic land type of your choice until end of turn.
        this.getSpellAbility().addEffect(new BecomesBasicLandTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        
        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false)
                .concatBy("<br>"));
    }

    private Jinx(final Jinx card) {
        super(card);
    }

    @Override
    public Jinx copy() {
        return new Jinx(this);
    }
}

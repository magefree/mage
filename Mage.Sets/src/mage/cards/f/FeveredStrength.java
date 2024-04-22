
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class FeveredStrength extends CardImpl {

    public FeveredStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Target creature gets +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse), false)
                .concatBy("<br>"));
    }

    private FeveredStrength(final FeveredStrength card) {
        super(card);
    }

    @Override
    public FeveredStrength copy() {
        return new FeveredStrength(this);
    }
}

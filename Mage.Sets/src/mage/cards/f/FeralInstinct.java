
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
public final class FeralInstinct extends CardImpl {

    public FeralInstinct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1), Duration.OneUse), false)
                .concatBy("<br>"));
    }

    private FeralInstinct(final FeralInstinct card) {
        super(card);
    }

    @Override
    public FeralInstinct copy() {
        return new FeralInstinct(this);
    }
}

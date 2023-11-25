package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.common.CantBeRegeneratedTargetEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Gravebind extends CardImpl {

    public Gravebind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Target creature can't be regenerated this turn.
        getSpellAbility().addEffect(new CantBeRegeneratedTargetEffect(Duration.EndOfTurn));
        getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                new DrawCardSourceControllerEffect(1)), false).concatBy("<br>"));
    }

    private Gravebind(final Gravebind card) {
        super(card);
    }

    @Override
    public Gravebind copy() {
        return new Gravebind(this);
    }
}

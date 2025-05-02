package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.condition.common.BeforeBlockersAreDeclaredCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class GorillaWarCry extends CardImpl {

    public GorillaWarCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Cast Gorilla War Cry only during combat before blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, BeforeBlockersAreDeclaredCondition.instance));

        // All creatures gain menace until end of turn. <i>(They can't be blocked except by two or more creatures.)</i>
        Effect effect = new GainAbilityAllEffect(new MenaceAbility(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText("All creatures gain menace until end of turn. <i>(They can't be blocked except by two or more creatures.)</i>");
        this.getSpellAbility().addEffect(effect);

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(
                new DrawCardSourceControllerEffect(1)), false).concatBy("<br>"));
    }

    private GorillaWarCry(final GorillaWarCry card) {
        super(card);
    }

    @Override
    public GorillaWarCry copy() {
        return new GorillaWarCry(this);
    }
}

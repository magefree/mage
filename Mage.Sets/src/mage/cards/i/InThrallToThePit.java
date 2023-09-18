package mage.cards.i;

import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InThrallToThePit extends CardImpl {

    public InThrallToThePit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Kicker {2}{B}
        this.addAbility(new KickerAbility("{2}{B}"));

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. If this spell was kicked, sacrifice that creature at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new SacrificeTargetEffect("sacrifice that creature")
                )), KickedCondition.ONCE, "If this spell was kicked, " +
                "sacrifice that creature at the beginning of the next end step"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private InThrallToThePit(final InThrallToThePit card) {
        super(card);
    }

    @Override
    public InThrallToThePit copy() {
        return new InThrallToThePit(this);
    }
}

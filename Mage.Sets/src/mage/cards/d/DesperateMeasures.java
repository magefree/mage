package mage.cards.d;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * Desperate Measures implementation
 */
public final class DesperateMeasures extends CardImpl {

    public DesperateMeasures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets +1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, -1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // When it dies under your control this turn, draw two cards.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new WhenTargetDiesDelayedTriggeredAbility(
                new DrawCardSourceControllerEffect(2), Duration.EndOfTurn
        )));
    }

    private DesperateMeasures(final DesperateMeasures card) {
        super(card);
    }

    @Override
    public DesperateMeasures copy() {
        return new DesperateMeasures(this);
    }
}
package mage.cards.d;

import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesperateMeasures extends CardImpl {

    public DesperateMeasures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets +1/-1 until end of turn. When it dies under your control this turn, draw two cards.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, -1));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new WhenTargetDiesDelayedTriggeredAbility(
                        new DrawCardSourceControllerEffect(2), Duration.EndOfTurn,
                        SetTargetPointer.NONE, true
                ), true
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DesperateMeasures(final DesperateMeasures card) {
        super(card);
    }

    @Override
    public DesperateMeasures copy() {
        return new DesperateMeasures(this);
    }
}

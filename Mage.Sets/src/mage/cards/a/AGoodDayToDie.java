package mage.cards.a;

import java.util.UUID;

import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author muz
 */
public final class AGoodDayToDie extends CardImpl {

    public AGoodDayToDie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets +2/+0 until end of turn. When that creature dies this turn, you draw a card.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
            new WhenTargetDiesDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1, true))
        ));

        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AGoodDayToDie(final AGoodDayToDie card) {
        super(card);
    }

    @Override
    public AGoodDayToDie copy() {
        return new AGoodDayToDie(this);
    }
}

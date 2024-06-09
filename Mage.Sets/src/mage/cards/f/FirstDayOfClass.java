package mage.cards.f;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.LearnEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class FirstDayOfClass extends CardImpl {

    public FirstDayOfClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Whenever a creature enters the battlefield under your control this turn, put a +1/+1 counter on it and it gains haste until end of turn.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new FirstDayOfClassTriggeredAbility()));

        // Learn.
        this.getSpellAbility().addEffect(new LearnEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(OpenSideboardHint.instance);
    }

    private FirstDayOfClass(final FirstDayOfClass card) {
        super(card);
    }

    @Override
    public FirstDayOfClass copy() {
        return new FirstDayOfClass(this);
    }
}

class FirstDayOfClassTriggeredAbility extends DelayedTriggeredAbility {

    public FirstDayOfClassTriggeredAbility() {
        super(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), Duration.EndOfTurn, false);
        this.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn));
    }

    private FirstDayOfClassTriggeredAbility(final FirstDayOfClassTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FirstDayOfClassTriggeredAbility copy() {
        return new FirstDayOfClassTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent.isCreature(game) && permanent.isControlledBy(this.getControllerId())) {
            getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under your control this turn, put a +1/+1 counter on it and it gains haste until end of turn";
    }
}
package mage.cards.f;

import java.util.UUID;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.OrCondition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.turn.Turn;

/**
 *
 * @author Jmlundeen
 */
public final class FullThrottle extends CardImpl {

    public FullThrottle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");


        // After this main phase, there are two additional combat phases.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AdditionalCombatPhaseEffect(2, TargetController.ANY),
                new OrCondition(new IsPhaseCondition(TurnPhase.PRECOMBAT_MAIN), new IsPhaseCondition(TurnPhase.POSTCOMBAT_MAIN)),
                "After this main phase, there are two additional combat phases."
        ));
        // At the beginning of each combat this turn, untap all creatures that attacked this turn.
        DelayedTriggeredAbility ability = new FullThrottleTriggeredAbility();
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(ability).concatBy("<br>"));
    }

    private FullThrottle(final FullThrottle card) {
        super(card);
    }

    @Override
    public FullThrottle copy() {
        return new FullThrottle(this);
    }
}

class FullThrottleTriggeredAbility extends DelayedTriggeredAbility {

    public FullThrottleTriggeredAbility() {
        super(new UntapAllThatAttackedEffect(), Duration.EndOfTurn, false);
        setTriggerPhrase("At the beginning of each combat this turn, ");
    }

    private FullThrottleTriggeredAbility(final FullThrottleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FullThrottleTriggeredAbility copy() {
        return new FullThrottleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COMBAT_PHASE_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Turn turn = game.getState().getTurn();
        return turn.getPhase().getType() == TurnPhase.COMBAT;
    }
}
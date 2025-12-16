package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackedThisTurnPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class FullThrottle extends CardImpl {

    public FullThrottle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{R}");

        // After this main phase, there are two additional combat phases.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AdditionalCombatPhaseEffect(2), FullThrottleCondition.instance,
                "After this main phase, there are two additional combat phases."
        ));

        // At the beginning of each combat this turn, untap all creatures that attacked this turn.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new FullThrottleTriggeredAbility())
                .concatBy("<br>"));
    }

    private FullThrottle(final FullThrottle card) {
        super(card);
    }

    @Override
    public FullThrottle copy() {
        return new FullThrottle(this);
    }
}

enum FullThrottleCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getTurnPhaseType().isMain();
    }
}

class FullThrottleTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creatures that attacked this turn");

    static {
        filter.add(AttackedThisTurnPredicate.instance);
    }

    public FullThrottleTriggeredAbility() {
        super(new UntapAllEffect(filter), Duration.EndOfTurn, false, false);
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
        return true;
    }
}

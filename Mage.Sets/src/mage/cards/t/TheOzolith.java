package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCountersCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheOzolith extends CardImpl {

    public TheOzolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever a creature you control leaves the battlefield, if it had counters on it, put those counters on The Ozolith.
        this.addAbility(new TheOzolithTriggeredAbility());

        // At the beginning of combat on your turn, if The Ozolith has counters on it, you may move all counters from The Ozolith onto target creature.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new TheOzolithMoveCountersEffect(), TargetController.YOU, true
                ), SourceHasCountersCondition.instance, "At the beginning of combat on your turn, " +
                "if {this} has counters on it, you may move all counters from {this} onto target creature."
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TheOzolith(final TheOzolith card) {
        super(card);
    }

    @Override
    public TheOzolith copy() {
        return new TheOzolith(this);
    }
}

class TheOzolithTriggeredAbility extends LeavesBattlefieldAllTriggeredAbility {

    TheOzolithTriggeredAbility() {
        super(null, StaticFilters.FILTER_CONTROLLED_CREATURE);
    }

    private TheOzolithTriggeredAbility(final TheOzolithTriggeredAbility ability) {
        super(ability);
    }

    public TheOzolithTriggeredAbility copy() {
        return new TheOzolithTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        Counters counters = permanent.getCounters(game);
        if (counters.values().stream().mapToInt(Counter::getCount).noneMatch(x -> x > 0)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new TheOzolithLeaveEffect(counters));
        return true;
    }

    public String getRule() {
        return "Whenever a creature you control leaves the battlefield, " +
                "if it had counters on it, put those counters on {this}.";
    }
}

class TheOzolithLeaveEffect extends OneShotEffect {

    private final Counters counters;

    TheOzolithLeaveEffect(Counters counters) {
        super(Outcome.Benefit);
        this.counters = counters.copy();
    }

    private TheOzolithLeaveEffect(final TheOzolithLeaveEffect effect) {
        super(effect);
        this.counters = effect.counters.copy();
    }

    @Override
    public TheOzolithLeaveEffect copy() {
        return new TheOzolithLeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        counters.values()
                .forEach(counter -> permanent.addCounters(counter, source.getControllerId(), source, game));
        return true;
    }
}

class TheOzolithMoveCountersEffect extends OneShotEffect {

    TheOzolithMoveCountersEffect() {
        super(Outcome.Benefit);
    }

    private TheOzolithMoveCountersEffect(final TheOzolithMoveCountersEffect effect) {
        super(effect);
    }

    @Override
    public TheOzolithMoveCountersEffect copy() {
        return new TheOzolithMoveCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (permanent == null || creature == null) {
            return false;
        }
        permanent.getCounters(game)
                .copy()
                .values()
                .stream()
                .filter(counter -> creature.addCounters(counter, source.getControllerId(), source, game))
                .forEach(counter -> permanent.removeCounters(counter, source, game));
        return true;
    }
}

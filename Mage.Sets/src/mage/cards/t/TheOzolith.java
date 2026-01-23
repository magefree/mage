package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.condition.common.SourceHasCountersCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheOzolith extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public TheOzolith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever a creature you control leaves the battlefield, if it had counters on it, put those counters on The Ozolith.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(new TheOzolithLeaveEffect(), filter));

        // At the beginning of combat on your turn, if The Ozolith has counters on it, you may move all counters from The Ozolith onto target creature.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new TheOzolithMoveCountersEffect(), true
        ).withInterveningIf(SourceHasCountersCondition.instance);
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

class TheOzolithLeaveEffect extends OneShotEffect {

    TheOzolithLeaveEffect() {
        super(Outcome.Benefit);
        staticText = "if it had counters on it, put those counters on {this}";
    }

    private TheOzolithLeaveEffect(final TheOzolithLeaveEffect effect) {
        super(effect);
    }

    @Override
    public TheOzolithLeaveEffect copy() {
        return new TheOzolithLeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) getValue("permanentLeftBattlefield");
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (creature == null || permanent == null) {
            return false;
        }
        for (Counter counter : creature.getCounters(game).copy().values()) {
            permanent.addCounters(counter, source, game);
        }
        return true;
    }
}

class TheOzolithMoveCountersEffect extends OneShotEffect {

    TheOzolithMoveCountersEffect() {
        super(Outcome.Benefit);
        staticText = "you may move all counters from {this} onto target creature";
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

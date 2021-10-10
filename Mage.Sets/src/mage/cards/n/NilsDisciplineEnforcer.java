package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayManaAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NilsDisciplineEnforcer extends CardImpl {

    public NilsDisciplineEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, for each player, put a +1/+1 counter on up to one target creature that player controls.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new NilsDisciplineEnforcerCountersEffect(), TargetController.YOU, false
        );
        ability.setTargetAdjuster(NilsDisciplineEnforcerAdjuster.instance);
        this.addAbility(ability);

        // Each creature with one or more counters on it can't attack you or planeswalkers you control unless its controller pays {X}, where X is the number of counters on that creature.
        this.addAbility(new SimpleStaticAbility(new NilsDisciplineEnforcerEffect()));
    }

    private NilsDisciplineEnforcer(final NilsDisciplineEnforcer card) {
        super(card);
    }

    @Override
    public NilsDisciplineEnforcer copy() {
        return new NilsDisciplineEnforcer(this);
    }
}

enum NilsDisciplineEnforcerAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        game.getState().getPlayersInRange(ability.getControllerId(), game).forEach(playerId -> {
            Player player = game.getPlayer(playerId);
            if (!(player == null)) {
                FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + player.getName());
                filter.add(new ControllerIdPredicate(playerId));
                ability.addTarget(new TargetPermanent(0, 1, filter));
            }
        });
    }
}

class NilsDisciplineEnforcerCountersEffect extends OneShotEffect {

    NilsDisciplineEnforcerCountersEffect() {
        super(Outcome.Benefit);
        staticText = "for each player, put a +1/+1 counter on up to one target creature that player controls";
    }

    private NilsDisciplineEnforcerCountersEffect(final NilsDisciplineEnforcerCountersEffect effect) {
        super(effect);
    }

    @Override
    public NilsDisciplineEnforcerCountersEffect copy() {
        return new NilsDisciplineEnforcerCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getTargets()
                .stream()
                .filter(
                        t -> (t != null))
                .map(t -> game.getPermanent(t.getFirstTarget()))
                .filter(targetedPermanent
                        -> (targetedPermanent != null))
                .forEachOrdered(targetedPermanent -> {
                    targetedPermanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                });
        return true;
    }
}

class NilsDisciplineEnforcerEffect extends CantAttackYouUnlessPayManaAllEffect {

    NilsDisciplineEnforcerEffect() {
        super(null, true);
        staticText = "Each creature with one or more counters on it can't attack you or planeswalkers you control "
                + "unless its controller pays {X}, where X is the number of counters on that creature.";
    }

    private NilsDisciplineEnforcerEffect(NilsDisciplineEnforcerEffect effect) {
        super(effect);
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent == null) {
            return null;
        }
        int count = permanent
                .getCounters(game)
                .keySet()
                .stream()
                .mapToInt(permanent.getCounters(game)::getCount)
                .sum();
        if (count < 1) {
            return null;
        }
        return new ManaCostsImpl("{" + count + '}');
    }

    @Override
    public NilsDisciplineEnforcerEffect copy() {
        return new NilsDisciplineEnforcerEffect(this);
    }
}

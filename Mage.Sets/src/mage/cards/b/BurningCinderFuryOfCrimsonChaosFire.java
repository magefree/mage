
package mage.cards.b;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 *
 * @author L_J
 */
public final class BurningCinderFuryOfCrimsonChaosFire extends CardImpl {

    public BurningCinderFuryOfCrimsonChaosFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever any player taps a permanent, that player choose one of their opponents. The chosen player gains control of that permanent at the beginning of the next end step.
        this.addAbility(new BurningCinderFuryOfCrimsonChaosFireAbility());

        // At the beginning of each player’s end step, if that player didn’t tap any nonland permanents that turn, Burning Cinder Fury of Crimson Chaos Fire deals 3 damage to that player.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new DamageTargetEffect(3).setText("{this} deals 3 damage to that player"), 
                TargetController.ANY, new BurningCinderFuryOfCrimsonChaosFireCondition(), false), new BurningCinderFuryOfCrimsonChaosFireWatcher());
    }

    public BurningCinderFuryOfCrimsonChaosFire(final BurningCinderFuryOfCrimsonChaosFire card) {
        super(card);
    }

    @Override
    public BurningCinderFuryOfCrimsonChaosFire copy() {
        return new BurningCinderFuryOfCrimsonChaosFire(this);
    }
}

class BurningCinderFuryOfCrimsonChaosFireAbility extends TriggeredAbilityImpl {

    public BurningCinderFuryOfCrimsonChaosFireAbility() {
        super(Zone.BATTLEFIELD, new BurningCinderFuryOfCrimsonChaosFireEffect(), false);
    }

    public BurningCinderFuryOfCrimsonChaosFireAbility(BurningCinderFuryOfCrimsonChaosFireAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            BurningCinderFuryOfCrimsonChaosFireEffect effect = (BurningCinderFuryOfCrimsonChaosFireEffect) this.getEffects().get(0);
            effect.setTargetPointer(new FixedTarget(permanent.getId()));
            effect.setFirstController(permanent.getControllerId()); // it's necessary to remember the original controller, as the controller might change by the time the trigger resolves
            return true;
        }
        return false;
    }

    @Override
    public BurningCinderFuryOfCrimsonChaosFireAbility copy() {
        return new BurningCinderFuryOfCrimsonChaosFireAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever any player taps a permanent, " + super.getRule();
    }
}

class BurningCinderFuryOfCrimsonChaosFireEffect extends OneShotEffect {

    private UUID firstController = null;

    public BurningCinderFuryOfCrimsonChaosFireEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player choose one of their opponents. The chosen player gains control of that permanent at the beginning of the next end step";
    }

    public BurningCinderFuryOfCrimsonChaosFireEffect(final BurningCinderFuryOfCrimsonChaosFireEffect effect) {
        super(effect);
        this.firstController = effect.firstController;
    }

    @Override
    public BurningCinderFuryOfCrimsonChaosFireEffect copy() {
        return new BurningCinderFuryOfCrimsonChaosFireEffect(this);
    }
    
    public void setFirstController(UUID newId) {
        this.firstController = newId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(firstController);
        if (player != null) {
            Target target = new TargetOpponent(true);
            if (target.canChoose(player.getId(), game)) {
                while (!target.isChosen() && target.canChoose(player.getId(), game) && player.canRespond()) {
                    player.chooseTarget(outcome, target, source, game);
                }
            }
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            Player chosenOpponent = game.getPlayer(target.getFirstTarget());
            
            if (permanent != null && chosenOpponent != null) {
                game.informPlayers(player.getLogName() + " chose " + chosenOpponent.getLogName() + " to gain control of " + permanent.getLogName() + " at the beginning of the next end step");
                ContinuousEffect effect = new BurningCinderFuryOfCrimsonChaosFireCreatureGainControlEffect(Duration.Custom, chosenOpponent.getId());
                effect.setTargetPointer(new FixedTarget(permanent.getId()));
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                return true;
            }
        }
        return false;
    }
}

class BurningCinderFuryOfCrimsonChaosFireCreatureGainControlEffect extends ContinuousEffectImpl {

    private UUID controller;

    public BurningCinderFuryOfCrimsonChaosFireCreatureGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
        this.staticText = "the chosen player gains control of that permanent";
    }

    public BurningCinderFuryOfCrimsonChaosFireCreatureGainControlEffect(final BurningCinderFuryOfCrimsonChaosFireCreatureGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public BurningCinderFuryOfCrimsonChaosFireCreatureGainControlEffect copy() {
        return new BurningCinderFuryOfCrimsonChaosFireCreatureGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (targetPointer != null) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
        }
        if (permanent != null && controller != null) {
            return permanent.changeControllerId(controller, game);
        }
        return false;
    }
}

class BurningCinderFuryOfCrimsonChaosFireCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        BurningCinderFuryOfCrimsonChaosFireWatcher watcher = (BurningCinderFuryOfCrimsonChaosFireWatcher) game.getState().getWatchers().get(BurningCinderFuryOfCrimsonChaosFireWatcher.class.getSimpleName());
        if (watcher != null) {
            return !watcher.tappedNonlandThisTurn(game.getActivePlayerId());
        }
        return false;
    }

    public String toString() {
        return "if that player didn’t tap any nonland permanents that turn";
    }
}

class BurningCinderFuryOfCrimsonChaosFireWatcher extends Watcher {

    private final Set<UUID> tappedActivePlayerIds = new HashSet<>();

    public BurningCinderFuryOfCrimsonChaosFireWatcher() {
        super(BurningCinderFuryOfCrimsonChaosFireWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public BurningCinderFuryOfCrimsonChaosFireWatcher(final BurningCinderFuryOfCrimsonChaosFireWatcher watcher) {
        super(watcher);
        this.tappedActivePlayerIds.addAll(watcher.tappedActivePlayerIds);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && !permanent.isLand()) {
                tappedActivePlayerIds.add(permanent.getControllerId());
            }
        }
    }

    public boolean tappedNonlandThisTurn(UUID playerId) {
        return tappedActivePlayerIds.contains(playerId);
    }

    @Override
    public void reset() {
        tappedActivePlayerIds.clear();
    }

    @Override
    public BurningCinderFuryOfCrimsonChaosFireWatcher copy() {
        return new BurningCinderFuryOfCrimsonChaosFireWatcher(this);
    }
}

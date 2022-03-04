package mage.cards.b;

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
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.*;

/**
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

    private BurningCinderFuryOfCrimsonChaosFire(final BurningCinderFuryOfCrimsonChaosFire card) {
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
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.setFirstControllerId(permanent.getControllerId()); // it's necessary to remember the original controller, as the controller might change by the time the trigger resolves
            return true;
        }
        return false;
    }

    @Override
    public BurningCinderFuryOfCrimsonChaosFireAbility copy() {
        return new BurningCinderFuryOfCrimsonChaosFireAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever any player taps a permanent, ";
    }
}

class BurningCinderFuryOfCrimsonChaosFireEffect extends OneShotEffect {

    private UUID firstControllerId = null;

    public BurningCinderFuryOfCrimsonChaosFireEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player choose one of their opponents. The chosen player gains control of that permanent at the beginning of the next end step";
    }

    public BurningCinderFuryOfCrimsonChaosFireEffect(final BurningCinderFuryOfCrimsonChaosFireEffect effect) {
        super(effect);
        this.firstControllerId = effect.firstControllerId;
    }

    @Override
    public BurningCinderFuryOfCrimsonChaosFireEffect copy() {
        return new BurningCinderFuryOfCrimsonChaosFireEffect(this);
    }

    public void setFirstControllerId(UUID newId) {
        this.firstControllerId = newId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player tappingPlayer = game.getPlayer(firstControllerId);
        Permanent permanentToControl = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (tappingPlayer != null && permanentToControl != null) {
            // Create opponent filter list manually because otherwise opponent check prevents controller of this to be valid
            FilterPlayer filter = new FilterPlayer("opponent to control " + permanentToControl.getIdName());
            List<PlayerIdPredicate> opponentPredicates = new ArrayList<>();
            for (UUID opponentId : game.getOpponents(firstControllerId)) {
                opponentPredicates.add(new PlayerIdPredicate(opponentId));
            }
            filter.add(Predicates.or(opponentPredicates));
            Target target = new TargetPlayer(1, 1, true, filter);
            target.setTargetController(firstControllerId);
            target.setAbilityController(source.getControllerId());
            if (tappingPlayer.chooseTarget(outcome, target, source, game)) {
                Player chosenOpponent = game.getPlayer(target.getFirstTarget());
                if (chosenOpponent != null) {
                    game.informPlayers(tappingPlayer.getLogName() + " chose " + chosenOpponent.getLogName() + " to gain control of " + permanentToControl.getLogName() + " at the beginning of the next end step");
                    ContinuousEffect effect = new BurningCinderFuryOfCrimsonChaosFireCreatureGainControlEffect(Duration.Custom, chosenOpponent.getId());
                    effect.setTargetPointer(new FixedTarget(permanentToControl.getId(), game));
                    game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
                    return true;
                }
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
            return permanent.changeControllerId(controller, game, source);
        }
        return false;
    }
}

class BurningCinderFuryOfCrimsonChaosFireCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        BurningCinderFuryOfCrimsonChaosFireWatcher watcher = game.getState().getWatcher(BurningCinderFuryOfCrimsonChaosFireWatcher.class);
        if (watcher != null) {
            return !watcher.tappedNonlandThisTurn(game.getActivePlayerId());
        }
        return false;
    }

    public String toString() {
        return "if that player didn't tap any nonland permanents that turn";
    }
}

class BurningCinderFuryOfCrimsonChaosFireWatcher extends Watcher {

    private final Set<UUID> tappedActivePlayerIds = new HashSet<>();

    public BurningCinderFuryOfCrimsonChaosFireWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && !permanent.isLand(game)) {
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
}

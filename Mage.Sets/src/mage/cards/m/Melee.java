
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.BeforeBlockersAreDeclaredCondition;
import mage.abilities.condition.common.IsPhaseCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;
import mage.watchers.common.ChooseBlockersRedundancyWatcher;

/**
 *
 * @author L_J
 */
public final class Melee extends CardImpl {

    public Melee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        // Cast Melee only during your turn and only during combat before blockers are declared.
        Condition condition = new CompoundCondition(BeforeBlockersAreDeclaredCondition.instance,
                new IsPhaseCondition(TurnPhase.COMBAT),
                MyTurnCondition.instance);
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, null, condition, "Cast this spell only during your turn and only during combat before blockers are declared"));

        // You choose which creatures block this combat and how those creatures block.
        // (only the last resolved Melee spell's blocking effect applies)
        this.getSpellAbility().addEffect(new MeleeChooseBlockersEffect());
        this.getSpellAbility().addWatcher(new ChooseBlockersRedundancyWatcher());
        this.getSpellAbility().addEffect(new ChooseBlockersRedundancyWatcherIncrementEffect());

        // Whenever a creature attacks and isn't blocked this combat, untap it and remove it from combat.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new MeleeTriggeredAbility()));
    }

    public Melee(final Melee card) {
        super(card);
    }

    @Override
    public Melee copy() {
        return new Melee(this);
    }
    
    private class ChooseBlockersRedundancyWatcherIncrementEffect extends OneShotEffect {
    
        ChooseBlockersRedundancyWatcherIncrementEffect() {
            super(Outcome.Neutral);
        }
    
        ChooseBlockersRedundancyWatcherIncrementEffect(final ChooseBlockersRedundancyWatcherIncrementEffect effect) {
            super(effect);
        }
    
        @Override
        public boolean apply(Game game, Ability source) {
            ChooseBlockersRedundancyWatcher watcher = (ChooseBlockersRedundancyWatcher) game.getState().getWatchers().get(ChooseBlockersRedundancyWatcher.class.getSimpleName());
            if (watcher != null) {
                watcher.increment();
                return true;
            }
            return false;
        }
    
        @Override
        public ChooseBlockersRedundancyWatcherIncrementEffect copy() {
            return new ChooseBlockersRedundancyWatcherIncrementEffect(this);
        }
    }
}

class MeleeChooseBlockersEffect extends ContinuousRuleModifyingEffectImpl {

    public MeleeChooseBlockersEffect() {
        super(Duration.EndOfCombat, Outcome.Benefit, false, false);
        staticText = "You choose which creatures block this combat and how those creatures block";
    }

    public MeleeChooseBlockersEffect(final MeleeChooseBlockersEffect effect) {
        super(effect);
    }

    @Override
    public MeleeChooseBlockersEffect copy() {
        return new MeleeChooseBlockersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_BLOCKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ChooseBlockersRedundancyWatcher watcher = (ChooseBlockersRedundancyWatcher) game.getState().getWatchers().get(ChooseBlockersRedundancyWatcher.class.getSimpleName());
        watcher.decrement();
        watcher.copyCount--;
        if (watcher.copyCountApply > 0) {
            game.informPlayers(source.getSourceObject(game).getIdName() + " didn't apply");
            this.discard();
            return false;
        }
        watcher.copyCountApply = watcher.copyCount;
        Player blockController = game.getPlayer(source.getControllerId());
        if (blockController != null) {
            game.getCombat().selectBlockers(blockController, game);
            return true;
        }
        this.discard();
        return false;
    }
}

class MeleeTriggeredAbility extends DelayedTriggeredAbility {

    public MeleeTriggeredAbility() {
        super(new UntapTargetEffect(), Duration.EndOfCombat, false);
        this.addEffect(new RemoveFromCombatTargetEffect());
    }

    public MeleeTriggeredAbility(MeleeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNBLOCKED_ATTACKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getBlockers().isEmpty() && combatGroup.getAttackers().contains(event.getTargetId())) {
                    this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public MeleeTriggeredAbility copy() {
        return new MeleeTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks and isn't blocked this combat, untap it and remove it from combat.";
    }
}

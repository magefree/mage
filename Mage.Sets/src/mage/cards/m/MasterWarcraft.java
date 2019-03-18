
package mage.cards.m;

import java.util.*;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.BeforeAttackersAreDeclaredCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.CantAttackTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;
import mage.watchers.common.ChooseBlockersRedundancyWatcher;

/**
 *
 * @author L_J
 */
public final class MasterWarcraft extends CardImpl {

    public MasterWarcraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R/W}{R/W}");

        // Cast Master Warcraft only before attackers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(null, null, BeforeAttackersAreDeclaredCondition.instance, "Cast this spell only before attackers are declared"));

        // You choose which creatures attack this turn.
        this.getSpellAbility().addEffect(new MasterWarcraftChooseAttackersEffect());

        // You choose which creatures block this turn and how those creatures block.
        this.getSpellAbility().addEffect(new MasterWarcraftChooseBlockersEffect());


        // (only the last resolved Master Warcraft spell's effects apply)
        this.getSpellAbility().addWatcher(new MasterWarcraftCastWatcher());
        this.getSpellAbility().addEffect(new MasterWarcraftCastWatcherIncrementEffect());
        this.getSpellAbility().addWatcher(new ChooseBlockersRedundancyWatcher());
        this.getSpellAbility().addEffect(new ChooseBlockersRedundancyWatcherIncrementEffect());
    }

    public MasterWarcraft(final MasterWarcraft card) {
        super(card);
    }

    @Override
    public MasterWarcraft copy() {
        return new MasterWarcraft(this);
    }
    
    private class MasterWarcraftCastWatcherIncrementEffect extends OneShotEffect {
    
        MasterWarcraftCastWatcherIncrementEffect() {
            super(Outcome.Neutral);
        }
    
        MasterWarcraftCastWatcherIncrementEffect(final MasterWarcraftCastWatcherIncrementEffect effect) {
            super(effect);
        }
    
        @Override
        public boolean apply(Game game, Ability source) {
            MasterWarcraftCastWatcher watcher = game.getState().getWatcher(MasterWarcraftCastWatcher.class);
            if (watcher != null) {
                watcher.increment();
                return true;
            }
            return false;
        }
    
        @Override
        public MasterWarcraftCastWatcherIncrementEffect copy() {
            return new MasterWarcraftCastWatcherIncrementEffect(this);
        }
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
            ChooseBlockersRedundancyWatcher watcher = game.getState().getWatcher(ChooseBlockersRedundancyWatcher.class);
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

class MasterWarcraftChooseAttackersEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures that will attack this combat (creatures not chosen won't attack this combat)");
    static {
        filter.add(new ControllerPredicate(TargetController.ACTIVE));
    }

    public MasterWarcraftChooseAttackersEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "You choose which creatures attack this turn";
    }

    public MasterWarcraftChooseAttackersEffect(final MasterWarcraftChooseAttackersEffect effect) {
        super(effect);
    }

    @Override
    public MasterWarcraftChooseAttackersEffect copy() {
        return new MasterWarcraftChooseAttackersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARING_ATTACKERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MasterWarcraftCastWatcher watcher = game.getState().getWatcher(MasterWarcraftCastWatcher.class);
        if(watcher == null){
            return false;
        }
        watcher.decrement();
        if (watcher.copyCountApply > 0) {
            game.informPlayers(source.getSourceObject(game).getIdName() + " didn't apply");
            return false;
        }
        watcher.copyCountApply = watcher.copyCount;
        Player controller = game.getPlayer(source.getControllerId());
        Player attackingPlayer = game.getPlayer(game.getCombat().getAttackingPlayerId());
        if (controller != null && attackingPlayer != null && !attackingPlayer.getAvailableAttackers(game).isEmpty()) {
            Target target = new TargetCreaturePermanent(0, Integer.MAX_VALUE, filter, true);
            if (controller.chooseTarget(Outcome.Benefit, target, source, game)) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
                    
                    // Choose creatures that will be attacking this combat
                    if (target.getTargets().contains(permanent.getId())) {
                        RequirementEffect effect = new AttacksIfAbleTargetEffect(Duration.EndOfCombat);
                        effect.setText("");
                        effect.setTargetPointer(new FixedTarget(permanent.getId()));
                        game.addEffect(effect, source);
                        game.informPlayers(controller.getLogName() + " has decided that " + permanent.getLogName() + " attacks this combat if able");
                        
                    // All other creatures can't attack (unless they must attack)
                    } else {
                        boolean hasToAttack = false;
                        for (Map.Entry<RequirementEffect, Set<Ability>> entry : game.getContinuousEffects().getApplicableRequirementEffects(permanent, false, game).entrySet()) {
                            RequirementEffect effect2 = entry.getKey();
                            if (effect2.mustAttack(game)) {
                                hasToAttack = true;
                            }
                        }
                        if (!hasToAttack) {
                            RestrictionEffect effect = new CantAttackTargetEffect(Duration.EndOfCombat);
                            effect.setText("");
                            effect.setTargetPointer(new FixedTarget(permanent.getId()));
                            game.addEffect(effect, source);
                        }
                    }
                }
            }
        }
        return false; // the attack declaration resumes for the active player as normal
    }
}

class MasterWarcraftChooseBlockersEffect extends ContinuousRuleModifyingEffectImpl {

    public MasterWarcraftChooseBlockersEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        staticText = "You choose which creatures block this turn and how those creatures block";
    }

    public MasterWarcraftChooseBlockersEffect(final MasterWarcraftChooseBlockersEffect effect) {
        super(effect);
    }

    @Override
    public MasterWarcraftChooseBlockersEffect copy() {
        return new MasterWarcraftChooseBlockersEffect(this);
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
        ChooseBlockersRedundancyWatcher watcher = game.getState().getWatcher(ChooseBlockersRedundancyWatcher.class);
        if(watcher == null){
            return false;
        }
        watcher.decrement();
        if (watcher.copyCountApply > 0) {
            game.informPlayers(source.getSourceObject(game).getIdName() + " didn't apply");
            return false;
        }
        watcher.copyCountApply = watcher.copyCount;
        Player blockController = game.getPlayer(source.getControllerId());
        if (blockController != null) {
            game.getCombat().selectBlockers(blockController, game);
            return true;
        }
        return false;
    }
}

class MasterWarcraftCastWatcher extends Watcher {

    public int copyCount = 0;
    public int copyCountApply = 0;

    public MasterWarcraftCastWatcher() {
        super(WatcherScope.GAME);
    }

    public MasterWarcraftCastWatcher(final MasterWarcraftCastWatcher watcher) {
        super(watcher);
        this.copyCount = watcher.copyCount;
        this.copyCountApply = watcher.copyCountApply;
    }

    @Override
    public void reset() {
        copyCount = 0;
        copyCountApply = 0;
    }

    @Override
    public MasterWarcraftCastWatcher copy() {
        return new MasterWarcraftCastWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
    }

    public void increment() {
        copyCount++;
        copyCountApply = copyCount;
    }    

    public void decrement() {
        if (copyCountApply > 0) {
            copyCountApply--;
        }
    }
}

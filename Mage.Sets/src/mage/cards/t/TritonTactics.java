
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author LevelX2
 */
public final class TritonTactics extends CardImpl {

    public TritonTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Up to two target creatures each get +0/+3 until end of turn. Untap those creatures.
        // At this turn's next end of combat, tap each creature that was blocked by one of those
        // creatures this turn and it doesn't untap during its controller's next untap step.
        Effect effect = new BoostTargetEffect(0, 3, Duration.EndOfTurn);
        effect.setText("Up to two target creatures each get +0/+3 until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new TritonTacticsUntapTargetEffect());
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new TritonTacticsTriggeredAbility()));

        this.getSpellAbility().addWatcher(new BlockedCreaturesWatcher());

    }

    private TritonTactics(final TritonTactics card) {
        super(card);
    }

    @Override
    public TritonTactics copy() {
        return new TritonTactics(this);
    }
}

class TritonTacticsUntapTargetEffect extends OneShotEffect {

    public TritonTacticsUntapTargetEffect() {
        super(Outcome.Untap);
        staticText = "Untap those creatures";
    }

    public TritonTacticsUntapTargetEffect(final TritonTacticsUntapTargetEffect effect) {
        super(effect);
    }

    @Override
    public TritonTacticsUntapTargetEffect copy() {
        return new TritonTacticsUntapTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<String> targets = new HashSet<>();
        for (UUID target : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                permanent.untap(game);
                targets.add(CardUtil.getCardZoneString("", permanent.getId(), game));
            }
        }
        if (!targets.isEmpty()) {
            // save the targets for the watcher in a map with zone change counter (as the card is recast during combat it's neccessary to save with zone change counter)
            Map<Integer, Set<String>> targetMap;
            Object object = game.getState().getValue("targets" + source.getSourceId());
            if (object instanceof Map) {
                targetMap = (Map<Integer, Set<String>>) object;
            } else {
                targetMap = new HashMap<>();
            }
            targetMap.put(game.getCard(source.getSourceId()).getZoneChangeCounter(game), targets);
            if (object == null) {
                game.getState().setValue("targets" + source.getSourceId().toString(), targetMap);
            }
        }
        return true;
    }

}

class TritonTacticsTriggeredAbility extends DelayedTriggeredAbility {

    public TritonTacticsTriggeredAbility() {
        super(new TritonTacticsEndOfCombatEffect(), Duration.EndOfTurn, true);
    }

    public TritonTacticsTriggeredAbility(TritonTacticsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public TritonTacticsTriggeredAbility copy() {
        return new TritonTacticsTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "At this turn's next end of combat, ";
    }
}

class TritonTacticsEndOfCombatEffect extends OneShotEffect {

    public TritonTacticsEndOfCombatEffect() {
        super(Outcome.Benefit);
        this.staticText = "tap each creature that was blocked by one of those creatures this turn and it doesn't untap during its controller's next untap step";
    }

    public TritonTacticsEndOfCombatEffect(final TritonTacticsEndOfCombatEffect effect) {
        super(effect);
    }

    @Override
    public TritonTacticsEndOfCombatEffect copy() {
        return new TritonTacticsEndOfCombatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<Integer, Set<String>> attackerMap = null;
        Object object = game.getState().getValue("blockedAttackers" + source.getSourceId());
        if (object instanceof Map) {
            attackerMap = (Map<Integer, Set<String>>) object;
            for (Set<String> attackerSet : attackerMap.values()) {
                List<Permanent> doNotUntapNextUntapStep = new ArrayList<>();
                for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                    if (attackerSet.contains(CardUtil.getCardZoneString(null, creature.getId(), game))) {
                        // tap creature and add the not untap effect
                        creature.tap(source, game);
                        doNotUntapNextUntapStep.add(creature);
                        game.informPlayers("Triton Tactics: " + creature.getName() + " doesn't untap during its controller's next untap step");
                    }
                }
                if (!doNotUntapNextUntapStep.isEmpty()) {
                    ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect("This creature");
                    effect.setTargetPointer(new FixedTargets(doNotUntapNextUntapStep, game));
                    game.addEffect(effect, source);
                }
            }
        }
        if (attackerMap != null) {
            attackerMap.clear();
        }

        return true;
    }
}

class BlockedCreaturesWatcher extends Watcher {

    public BlockedCreaturesWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Map<Integer, Set<String>> targetMap;
            Object object = game.getState().getValue("targets" + this.getSourceId().toString());
            if (object instanceof Map) {
                Permanent blocker = game.getPermanent(event.getSourceId());
                if (blocker != null) {
                    targetMap = (Map<Integer, Set<String>>) object;
                    for (Map.Entry<Integer, Set<String>> entry : targetMap.entrySet()) {
                        if (entry.getValue().contains(CardUtil.getCardZoneString("", blocker.getId(), game))) {
                            // save the attacking creature that was blocked by a creature effected by Triton Tactics
                            saveAttackingCreature(event.getTargetId(), entry.getKey(), game);
                        }
                    }
                }
            }

        }
    }

    private void saveAttackingCreature(UUID attackerId, Integer zoneChangeCounter, Game game) {
        Set<String> attackers;
        Map<Integer, Set<String>> attackerMap;
        Object object = game.getState().getValue("blockedAttackers" + getSourceId());
        if (object instanceof Map) {
            attackerMap = (Map<Integer, Set<String>>) object;
        } else {
            attackerMap = new HashMap<>();
        }
        attackers = attackerMap.computeIfAbsent(zoneChangeCounter, k -> new HashSet<>());
        attackers.add(CardUtil.getCardZoneString(null, attackerId, game));
        game.getState().setValue("blockedAttackers" + getSourceId().toString(), attackerMap);
    }
}

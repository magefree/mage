/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.theros;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SkipNextUntapTargetEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class TritonTactics extends CardImpl<TritonTactics> {

    public TritonTactics(UUID ownerId) {
        super(ownerId, 71, "Triton Tactics", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "THS";

        this.color.setBlue(true);

        // Up to two target creatures each get +0/+3 until end of turn. Untap those creatures.
        // At this turn's next end of combat, tap each creature that was blocked by one of those
        // creatures this turn and it doesn't untap during its controller's next untap step.
        Effect effect = new BoostTargetEffect(0,3, Duration.EndOfTurn);
        effect.setText("Up to two target creatures each get +0/+3 until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getSpellAbility().addEffect(new TritonTacticsUntapTargetEffect());
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new TritonTacticsTriggeredAbility()));

        this.addWatcher(new BlockedCreaturesWatcher());


    }

    public TritonTactics(final TritonTactics card) {
        super(card);
    }

    @Override
    public TritonTactics copy() {
        return new TritonTactics(this);
    }
}

class TritonTacticsUntapTargetEffect extends OneShotEffect<TritonTacticsUntapTargetEffect> {

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
        Set<String> targets = new HashSet<String>();
        for (UUID target: targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                permanent.untap(game);
                targets.add(CardUtil.getCardZoneString("", permanent.getId(), game));
            }
        }
        if (!targets.isEmpty()) {
            // save the targets for the watcher
            Map<Integer, Set<String>> targetMap;
            Object object = game.getState().getValue("targets" + source.getSourceId());
            if (object != null && object instanceof Map) {
                targetMap = (Map<Integer, Set<String>>) object;
            } else {
                targetMap = new HashMap<Integer, Set<String>>();
            }
            targetMap.put(new Integer(game.getCard(source.getSourceId()).getZoneChangeCounter()), targets);
            if (object == null) {
                game.getState().setValue("targets" + source.getSourceId().toString(), targetMap);
            }
        }
        return true;
    }

}

class TritonTacticsTriggeredAbility extends DelayedTriggeredAbility<TritonTacticsTriggeredAbility> {

    public TritonTacticsTriggeredAbility() {
        super(new TritonTacticsEndOfCombatEffect(), Duration.EndOfCombat, true);
    }

    public TritonTacticsTriggeredAbility(TritonTacticsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.END_COMBAT_STEP_PRE) {
            return true;
        }
        return false;
    }

    @Override
    public TritonTacticsTriggeredAbility copy() {
        return new TritonTacticsTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "At this turn's next end of combat, " + modes.getText();
    }
}

class TritonTacticsEndOfCombatEffect extends OneShotEffect<TritonTacticsEndOfCombatEffect> {

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
        if (object != null && object instanceof Map) {
            attackerMap = (Map<Integer, Set<String>>) object;
            for (Set<String> attackerSet :attackerMap.values()) {
                for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
                    if (attackerSet.contains(CardUtil.getCardZoneString(null, creature.getId(), game))) {
                        // tap creature and add the not untap effect
                        creature.tap(game);
                        ContinuousEffect effect  = new SkipNextUntapTargetEffect();
                        effect.setTargetPointer(new FixedTarget(creature.getId()));
                        game.addEffect(effect, source);
                        game.informPlayers(new StringBuilder("Triton Tactics: ").append(creature.getName()).append(" doesn't untap during its controller's next untap step").toString());
                    }
                }
            }
        }
        if (attackerMap != null) {
            attackerMap.clear();
        }

        return true;
    }
}

class BlockedCreaturesWatcher extends WatcherImpl<BlockedCreaturesWatcher> {

    public BlockedCreaturesWatcher() {
        super("BlockedCreatures", WatcherScope.CARD);
    }

    public BlockedCreaturesWatcher(final BlockedCreaturesWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BLOCKER_DECLARED) {
            Map<Integer, Set<String>> targetMap;
            Object object = game.getState().getValue("targets" + this.getSourceId().toString());
            if (object != null && object instanceof Map) {
                Permanent blocker = game.getPermanent(event.getSourceId());
                if (blocker != null) {
                    targetMap = (Map<Integer, Set<String>>) object;
                    for (Map.Entry<Integer, Set<String>> entry : targetMap.entrySet()) {
                        if (entry.getValue().contains(CardUtil.getCardZoneString("", blocker.getId(), game))) {
                            // save the attacking craeture that was blocked
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
        if (object != null && object instanceof Map) {
            attackerMap = (Map<Integer, Set<String>>) object;
        } else {
            attackerMap = new HashMap<Integer, Set<String>>();
        }
        attackers = attackerMap.get(zoneChangeCounter);
        if (attackers == null) {
            attackers = new HashSet<String>();
            attackerMap.put(zoneChangeCounter, attackers);
        }
        attackers.add(CardUtil.getCardZoneString(null, attackerId, game));
        if (object == null || !(object instanceof Map)) {
            game.getState().setValue("blockedAttackers" + getSourceId().toString(), attackerMap);
        }
    }

    @Override
    public BlockedCreaturesWatcher copy() {
        return new BlockedCreaturesWatcher(this);
    }
}

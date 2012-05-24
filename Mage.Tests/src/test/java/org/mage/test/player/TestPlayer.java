/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package org.mage.test.player;

import mage.Constants;
import mage.Constants.PhaseStep;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.choices.Choice;
import mage.counters.Counter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreatureForCombat;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.player.ai.ComputerPlayer;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import org.junit.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
@Ignore
public class TestPlayer extends ComputerPlayer<TestPlayer> {

    private List<PlayerAction> actions = new ArrayList<PlayerAction>();
    private List<String> choices = new ArrayList<String>();
    
    public TestPlayer(String name, Constants.RangeOfInfluence range) {
		super(name, range);
		human = false;
	}

    public TestPlayer(final TestPlayer player) {
		super(player);
	}

    public void addAction(int turnNum, PhaseStep step, String action) {
        actions.add(new PlayerAction(turnNum, step, action));
    }
    
    public void addChoice(String choice) {
        choices.add(choice);
    }
    
    @Override
    public TestPlayer copy() {
        return new TestPlayer(this);
    }

    @Override
    public boolean priority(Game game) {
        for (PlayerAction action: actions) {
            if (action.getTurnNum() == game.getTurnNum() && action.getStep() == game.getStep().getType()) {
                if (action.getAction().startsWith("activate:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("activate:") + 9);
                    String[] groups = command.split(";");
                    for (Ability ability: this.getPlayable(game, true)) {
                        if (ability.toString().startsWith(groups[0])) {
                            if (groups.length > 1) {
                                addTargets(ability, groups, game);
                            }
                            this.activateAbility((ActivatedAbility)ability, game);
                            actions.remove(action);
                            return true;
                        }
                    }                    
                }
                if (action.getAction().startsWith("addCounters:")) {
                    String command = action.getAction();
                    command = command.substring(command.indexOf("addCounters:") + 12);
                    String[] groups = command.split(";");
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
                        if (permanent.getName().equals(groups[0])) {
                            Counter counter = new Counter(groups[1], Integer.parseInt(groups[2]));
                            permanent.addCounters(counter, game);
                            break;
                        }
                    }
                }
            }
        }
        pass();
        return false;
    }

    @Override
    public void selectAttackers(Game game) {
        UUID opponentId = game.getCombat().getDefenders().iterator().next();
        for (PlayerAction action: actions) {
            if (action.getTurnNum() == game.getTurnNum() && action.getAction().startsWith("attack:")) {
                String command = action.getAction();
                command = command.substring(command.indexOf("attack:") + 7);
                FilterCreatureForCombat filter = new FilterCreatureForCombat();
                filter.getName().add(command);
                Permanent attacker = findPermanent(filter, playerId, game);
                if (attacker != null && attacker.canAttack(game)) {
                    this.declareAttacker(attacker.getId(), opponentId, game);
                }
            }
        }
    }

    @Override
    public void selectBlockers(Game game) {
        UUID opponentId = game.getOpponents(playerId).iterator().next();
        for (PlayerAction action: actions) {
            if (action.getTurnNum() == game.getTurnNum() && action.getAction().startsWith("block:")) {
                String command = action.getAction();
                command = command.substring(command.indexOf("block:") + 6);
                String[] groups = command.split(";");
                FilterCreatureForCombat filterBlocker = new FilterCreatureForCombat();
                filterBlocker.getName().add(groups[0]);
                Permanent blocker = findPermanent(filterBlocker, playerId, game);
                if (blocker != null) {
                    FilterAttackingCreature filterAttacker = new FilterAttackingCreature();
                    filterAttacker.getName().add(groups[1]);
                    Permanent attacker = findPermanent(filterAttacker, opponentId, game);
                    if (attacker != null) {
                        this.declareBlocker(blocker.getId(), attacker.getId(), game);
                    }
                }
            }
        }
    }
    
	@Override
	public boolean choose(Constants.Outcome outcome, Choice choice, Game game) {
        if (!choices.isEmpty()) {
            for (String choose1: choice.getChoices()) {
                for (String choose2: choices) {
                    if (choose1.equals(choose2)) {
                        choice.setChoice(choose2);
                        choices.remove(choose2);
                        return true;
                    }
                }
            }
        }
        return super.choose(outcome, choice, game);
	}

    @Override
    public boolean choose(Constants.Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
        if (!choices.isEmpty()) {
            if (target instanceof TargetPermanent) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents((FilterPermanent)target.getFilter(), game)) {
                    for (String choose2: choices) {
                        if (permanent.getName().equals(choose2)) {
                            if (((TargetPermanent)target).canTarget(playerId, permanent.getId(), null, game) && !target.getTargets().contains(permanent.getId())) {
                                target.add(permanent.getId(), game);
                                choices.remove(choose2);
                                return true;
                            }
                        } else if ((permanent.getName()+"-"+permanent.getExpansionSetCode()).equals(choose2)) {
                            if (((TargetPermanent)target).canTarget(playerId, permanent.getId(), null, game) && !target.getTargets().contains(permanent.getId())) {
                                target.add(permanent.getId(), game);
                                choices.remove(choose2);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return super.choose(outcome, target, sourceId, game, options);
    }
    
    protected Permanent findPermanent(FilterPermanent filter, UUID controllerId, Game game) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, controllerId, game);
        if (permanents.size() > 0)
            return permanents.get(0);
        return null;
    }

    private void addTargets(Ability ability, String[] groups, Game game) {
        for (int i = 1; i < groups.length; i++) {
            String group = groups[i];
            String target;
            if (group.startsWith("targetPlayer=")) {
                target = group.substring(group.indexOf("targetPlayer=") + 13);
                for (Player player: game.getPlayers().values()) {
                    if (player.getName().equals(target)) {
                        ability.getTargets().get(0).addTarget(player.getId(), ability, game);
                        break;
                    }
                }
            }
            else if (group.startsWith("target=")) {
                target = group.substring(group.indexOf("target=") + 7);
                String[] targets = target.split("\\^");
                int index = 0;
                for (String t: targets) {
                    if (t.startsWith("targetPlayer=")) {
                        target = t.substring(t.indexOf("targetPlayer=") + 13);
                        for (Player player: game.getPlayers().values()) {
                            if (player.getName().equals(target)) {
                                ability.getTargets().get(index).addTarget(player.getId(), ability, game);
                                index++;
                                break;
                            }
                        }
                    } else {
                        for (UUID id: ability.getTargets().get(0).possibleTargets(ability.getSourceId(), ability.getControllerId(), game)) {
                            MageObject object = game.getObject(id);
                            if (object != null && object.getName().equals(t)) {
                                if (ability.getTargets().get(index).getNumberOfTargets() == 1) {
                                    ability.getTargets().get(index).clearChosen();
                                }
                                ability.getTargets().get(index).addTarget(id, ability, game);
                                index++;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    
}

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
package mage.abilities.effects.common.counter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Outcome;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetPermanentOrPlayerWithCounter;


/**
 * @author nantuko
 */
public class ProliferateEffect extends OneShotEffect {

    public ProliferateEffect() {
        super(Outcome.Benefit);
        staticText = "Proliferate. <i>(You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)</i>";
    }

    public ProliferateEffect(ProliferateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetPermanentOrPlayerWithCounter(0, Integer.MAX_VALUE, true);
        Map<String, Serializable> options = new HashMap<>();
        options.put("UI.right.btn.text", "Done");
        controller.choose(Outcome.Benefit, target, source.getSourceId(), game, options);

        for (int idx = 0; idx < target.getTargets().size(); idx++) {
            UUID chosen = (UUID) target.getTargets().get(idx);
            Permanent permanent = game.getPermanent(chosen);
            if (permanent != null) {
                if (permanent.getCounters().size() > 0) {
                    if (permanent.getCounters().size() == 1) {
                        for (Counter counter : permanent.getCounters().values()) {
                            permanent.addCounters(counter.getName(), 1, game);
                        }
                    } else {
                        Choice choice = new ChoiceImpl(true);
                        Set<String> choices = new HashSet<>();
                        for (Counter counter : permanent.getCounters().values()) {
                            choices.add(counter.getName());
                        }
                        choice.setChoices(choices);
                        choice.setMessage("Choose a counter to proliferate (" + permanent.getName() + ")");
                        controller.choose(Outcome.Benefit, choice, game);
                        for (Counter counter : permanent.getCounters().values()) {
                            if (counter.getName().equals(choice.getChoice())) {
                                permanent.addCounters(counter.getName(), 1, game);
                                break;
                            }
                        }
                    }
                }
            } else {
                Player player = game.getPlayer(chosen);
                if (player != null) {
                    if (player.getCounters().size() > 0) {
                        if (player.getCounters().size() == 1) {
                            for (Counter counter : player.getCounters().values()) {
                                Counter newCounter = new Counter(counter.getName());
                                player.addCounters(newCounter, game);
                            }
                        } else {
                            Choice choice = new ChoiceImpl(true);
                            Set<String> choices = new HashSet<>();
                            for (Counter counter : player.getCounters().values()) {
                                choices.add(counter.getName());
                            }
                            choice.setChoices(choices);
                            choice.setMessage("Choose a counter to proliferate (" + player.getLogName() + ")");
                            controller.choose(Outcome.Benefit, choice, game);
                            for (Counter counter : player.getCounters().values()) {
                                if (counter.getName().equals(choice.getChoice())) {
                                    Counter newCounter = new Counter(counter.getName());
                                    player.addCounters(newCounter, game);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public ProliferateEffect copy() {
        return new ProliferateEffect(this);
    }

}

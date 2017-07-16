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
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.SubTypeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Effect for the DevourAbility
 *
 * 702.81. Devour 702.81a Devour is a static ability. "Devour N" means "As this
 * object enters the battlefield, you may sacrifice any number of creatures.
 * This permanent enters the battlefield with N +1/+1 counters on it for each
 * creature sacrificed this way." 702.81b Some objects have abilities that refer
 * to the number of creatures the permanent devoured. "It devoured" means
 * "sacrificed as a result of its devour ability as it entered the battlefield."
 *
 *
 * @author LevelX2
 */
public class DevourEffect extends ReplacementEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creatures to devour");

    static {
        filter.add(new AnotherPredicate());
    }
    private final DevourFactor devourFactor;

    public DevourEffect(DevourFactor devourFactor) {
        super(Duration.EndOfGame, Outcome.Detriment);
        this.devourFactor = devourFactor;
    }

    public DevourEffect(final DevourEffect effect) {
        super(effect);
        this.devourFactor = effect.devourFactor;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            Permanent sourcePermanent = ((EntersTheBattlefieldEvent) event).getTarget();
            game.getState().setValue(sourcePermanent.getId().toString() + "devoured", null);
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            Target target = new TargetControlledCreaturePermanent(1, Integer.MAX_VALUE, filter, true);
            target.setRequired(false);
            if (!target.canChoose(source.getSourceId(), source.getControllerId(), game)) {
                return false;
            }
            if (controller.chooseUse(Outcome.Detriment, "Devour creatures?", source, game)) {
                controller.chooseTarget(Outcome.Detriment, target, source, game);
                if (!target.getTargets().isEmpty()) {
                    List<SubTypeList> cardSubtypes = new ArrayList<>();
                    int devouredCreatures = target.getTargets().size();
                    if (!game.isSimulation()) {
                        game.informPlayers(creature.getLogName() + " devours " + devouredCreatures + " creatures");
                    }
                    for (UUID targetId : target.getTargets()) {
                        Permanent targetCreature = game.getPermanent(targetId);
                        if (targetCreature != null) {
                            cardSubtypes.add(targetCreature.getSubtype(game));
                        }
                        if (targetCreature == null || !targetCreature.sacrifice(source.getSourceId(), game)) {
                            return false;
                        }
                    }
                    int amountCounters;
                    if (devourFactor == DevourFactor.DevourX) {
                        amountCounters = devouredCreatures * devouredCreatures;
                    } else {
                        amountCounters = devouredCreatures * devourFactor.getFactor();
                    }
                    creature.addCounters(CounterType.P1P1.createInstance(amountCounters), source, game);
                    game.getState().setValue(creature.getId().toString() + "devoured", cardSubtypes);
                }

            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder(devourFactor.toString());
        sb.append(" <i>(As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with ");
        sb.append(devourFactor.getRuleText()).append(")</i>");
        return sb.toString();
    }

    public List<ArrayList<String>> getSubtypes(Game game, UUID permanentId) {
        Object object = game.getState().getValue(permanentId.toString() + "devoured");
        if (object != null) {
            return (List<ArrayList<String>>) object;
        }
        return Collections.emptyList();
    }

    public int getDevouredCreaturesAmount(Game game, UUID permanentId) {
        Object object = game.getState().getValue(permanentId.toString() + "devoured");
        if (object != null) {
            return ((List<ArrayList<String>>) object).size();
        }
        return 0;
    }

    @Override
    public DevourEffect copy() {
        return new DevourEffect(this);
    }

    public enum DevourFactor {

        Devour1("Devour 1", "that many +1/+1 counters on it", 1),
        Devour2("Devour 2", "twice that many +1/+1 counters on it", 2),
        Devour3("Devour 3", "three times that many +1/+1 counters on it", 3),
        DevourX("Devour X, where X is the number of creatures devoured this way", "X +1/+1 counters on it for each of those creatures", Integer.MAX_VALUE);

        private final String text;
        private final String ruleText;
        private final int factor;

        DevourFactor(String text, String ruleText, int factor) {
            this.text = text;
            this.ruleText = ruleText;
            this.factor = factor;
        }

        @Override
        public String toString() {
            return text;
        }

        public String getRuleText() {
            return ruleText;
        }

        public int getFactor() {
            return factor;
        }
    }
}

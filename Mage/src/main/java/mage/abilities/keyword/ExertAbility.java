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
package mage.abilities.keyword;

import java.util.HashSet;
import java.util.Set;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BecomesExertSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class ExertAbility extends SimpleStaticAbility {

    private String ruleText;

    public ExertAbility(BecomesExertSourceTriggeredAbility ability) {
        this(ability, false);
    }

    public ExertAbility(BecomesExertSourceTriggeredAbility ability, boolean exertOnlyOncePerTurn) {
        super(Zone.BATTLEFIELD, new ExertReplacementEffect(exertOnlyOncePerTurn));
        ruleText = (exertOnlyOncePerTurn
                ? "If {this} hasn't been exerted this turn, you may exert it"
                : "You may exert {this}") + " as it attacks. ";
        if (ability != null) {
            this.addSubAbility(ability);
            ruleText += ("When you do, " +
                    ability.getEffects().get(0).getText(ability.getModes().getMode())
                    + ". ");
            ability.setRuleVisible(false);
        }
        ruleText += "<i>(An exterted creature can't untap during your next untap step)</i>";
        if (exertOnlyOncePerTurn) {
            getWatchers().add(new ExertedThisTurnWatcher());
        }
    }

    public ExertAbility(final ExertAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;

    }

    @Override
    public ExertAbility copy() {
        return new ExertAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}

class ExertReplacementEffect extends ReplacementEffectImpl {

    final private boolean exertOnlyOncePerTurn;

    public ExertReplacementEffect(boolean exertOnlyOncePerTurn) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You may exert {this} as it attacks";
        this.exertOnlyOncePerTurn = exertOnlyOncePerTurn;
    }

    public ExertReplacementEffect(ExertReplacementEffect effect) {
        super(effect);
        this.exertOnlyOncePerTurn = effect.exertOnlyOncePerTurn;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = game.getPermanent(event.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            if (exertOnlyOncePerTurn) {
                MageObjectReference creatureReference = new MageObjectReference(creature.getId(), creature.getZoneChangeCounter(game), game);
                ExertedThisTurnWatcher watcher = (ExertedThisTurnWatcher) game.getState().getWatchers().get(ExertedThisTurnWatcher.class.getName());
                if (watcher != null && watcher.getExertedThisTurnCreatures().contains(creatureReference)) {
                    return false;
                }
            }
            if (controller.chooseUse(outcome, "Exert " + creature.getLogName() + '?',
                    "An exterted creature can't untap during your next untap step.", "Yes", "No", source, game)) {
                if (!game.isSimulation()) {
                    game.informPlayers(controller.getLogName() + " exerted " + creature.getName());
                }
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.BECOMES_EXERTED, creature.getId(), creature.getId(), creature.getControllerId()));
                ContinuousEffect effect = new DontUntapInControllersNextUntapStepTargetEffect();
                effect.setTargetPointer(new FixedTarget(creature, game));
                game.addEffect(effect, source);
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }

    @Override
    public ExertReplacementEffect copy() {
        return new ExertReplacementEffect(this);
    }

}

class ExertedThisTurnWatcher extends Watcher {

    private final Set<MageObjectReference> exertedThisTurnCreatures;

    public ExertedThisTurnWatcher() {
        super(ExertedThisTurnWatcher.class.getName(), WatcherScope.GAME);
        exertedThisTurnCreatures = new HashSet<>();
    }

    public ExertedThisTurnWatcher(final ExertedThisTurnWatcher watcher) {
        super(watcher);
        exertedThisTurnCreatures = new HashSet<>(watcher.exertedThisTurnCreatures);
    }

    @Override
    public Watcher copy() {
        return new ExertedThisTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.BECOMES_EXERTED) {
            this.exertedThisTurnCreatures.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    public Set<MageObjectReference> getExertedThisTurnCreatures() {
        return this.exertedThisTurnCreatures;
    }

    @Override
    public void reset() {
        super.reset();
        exertedThisTurnCreatures.clear();
    }

}

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
package mage.sets.scourge;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

/**
 *
 * @author emerald000
 */
public class GripOfChaos extends CardImpl {

    public GripOfChaos(UUID ownerId) {
        super(ownerId, 98, "Grip of Chaos", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");
        this.expansionSetCode = "SCG";

        // Whenever a spell or ability is put onto the stack, if it has a single target, reselect its target at random.
        this.addAbility(new GripOfChaosTriggeredAbility());
    }

    public GripOfChaos(final GripOfChaos card) {
        super(card);
    }

    @Override
    public GripOfChaos copy() {
        return new GripOfChaos(this);
    }
}

class GripOfChaosTriggeredAbility extends TriggeredAbilityImpl {

    GripOfChaosTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GripOfChaosEffect());
    }

    GripOfChaosTriggeredAbility(final GripOfChaosTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST
                || event.getType() == EventType.ACTIVATED_ABILITY
                || event.getType() == EventType.TRIGGERED_ABILITY
                || event.getType() == EventType.COPIED_STACKOBJECT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        StackObject stackObject = null;
        for (Effect effect : this.getEffects()) {
            stackObject = game.getStack().getStackObject(effect.getTargetPointer().getFirst(game, this));
        }
        if (stackObject != null) {
            int numberOfTargets = 0;
            for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = stackObject.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    numberOfTargets += target.getTargets().size();
                }
            }
            return numberOfTargets == 1;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a spell or ability is put onto the stack, if it has a single target, reselect its target at random.";
    }

    @Override
    public GripOfChaosTriggeredAbility copy() {
        return new GripOfChaosTriggeredAbility(this);
    }
}

class GripOfChaosEffect extends OneShotEffect {

    GripOfChaosEffect() {
        super(Outcome.Neutral);
        this.staticText = "reselect its target at random";
    }

    GripOfChaosEffect(final GripOfChaosEffect effect) {
        super(effect);
    }

    @Override
    public GripOfChaosEffect copy() {
        return new GripOfChaosEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(this.getTargetPointer().getFirst(game, source));
        if (stackObject != null) {
            for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = stackObject.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    UUID oldTargetId = target.getFirstTarget();
                    Set<UUID> possibleTargets = target.possibleTargets(source.getSourceId(), source.getControllerId(), game);
                    if (possibleTargets.size() > 0) {
                        int i = 0;
                        int rnd = RandomUtil.nextInt(possibleTargets.size());
                        Iterator<UUID> it = possibleTargets.iterator();
                        while (i < rnd) {
                            it.next();
                            i++;
                        }
                        UUID newTargetId = it.next();
                        target.remove(oldTargetId);
                        target.add(newTargetId, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

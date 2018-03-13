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
package mage.cards.d;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class DivineIntervention extends CardImpl {

    public DivineIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{W}{W}");

        // Divine Intervention enters the battlefield with 2 intervention counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.INTERVENTION.createInstance(2));
        this.addAbility(new EntersBattlefieldAbility(effect, "with 2 intervention counters"));

        // At the beginning of your upkeep, remove an intervention counter from Divine Intervention.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new RemoveCounterSourceEffect(CounterType.INTERVENTION.createInstance()), TargetController.YOU, false));

        // Whenever a intervention counter is removed from Protean Hydra, put two intervention counters on it at the beginning of the next end step.
        this.addAbility(new DivineInterventionAbility());
    }

    public DivineIntervention(final DivineIntervention card) {
        super(card);
    }

    @Override
    public DivineIntervention copy() {
        return new DivineIntervention(this);
    }

    class DivineInterventionAbility extends TriggeredAbilityImpl {

        public DivineInterventionAbility() {
            super(Zone.BATTLEFIELD, new DivineAbilityEffect2(), false);
        }

        public DivineInterventionAbility(final DivineInterventionAbility ability) {
            super(ability);
        }

        @Override
        public DivineInterventionAbility copy() {
            return new DivineInterventionAbility(this);
        }

        @Override
        public boolean checkEventType(GameEvent event, Game game) {
            return event.getType() == EventType.COUNTER_REMOVED;
        }

        @Override
        public boolean checkTrigger(GameEvent event, Game game) {
            if (event.getData().equals(CounterType.INTERVENTION.getName()) && event.getTargetId().equals(this.getSourceId())) {

                boolean onlyYouOnStack = true;
                boolean onlyOpponentOnStack = true;
                UUID you = getControllerId();
                boolean firstOnStack = false;

                for (StackObject stackObject : game.getStack()) {

                    if (stackObject.getControllerId() != null && !firstOnStack) {
                        if (!Objects.equals(you, stackObject.getControllerId())) {
                            onlyYouOnStack = false;
                        } else if (Objects.equals(you, stackObject.getControllerId())) {
                            onlyOpponentOnStack = false;
                        }

                        firstOnStack = true;
                    }
                }

                if (onlyYouOnStack && !onlyOpponentOnStack) {
                    return true;
                } else if (!onlyYouOnStack && onlyOpponentOnStack) {
                    return false;
                }
            }
            return false;
        }

        @Override
        public String getRule() {
            return "When you remove the last intervention counter from {this}, the game is drawn.";
        }
    }

    class DivineAbilityEffect2 extends OneShotEffect {

        public DivineAbilityEffect2() {
            super(Outcome.Neutral);
            this.staticText = "you draw the game";
        }

        public DivineAbilityEffect2(final DivineAbilityEffect2 effect) {
            super(effect);
        }

        @Override
        public DivineAbilityEffect2 copy() {
            return new DivineAbilityEffect2(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (controller != null && sourcePermanent != null) {
                if (game.getState().getZone(sourcePermanent.getId()) == Zone.BATTLEFIELD && sourcePermanent.getCounters(game).getCount(CounterType.INTERVENTION) == 0) {
                    game.setDraw(controller.getId());
                }
                return true;
            }
            return false;
        }
    }
}

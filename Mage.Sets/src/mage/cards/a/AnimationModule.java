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
package mage.cards.a;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ServoToken;
import mage.players.Player;
import mage.target.common.TargetPermanentOrPlayer;

/**
 *
 * @author emerald000
 */
public class AnimationModule extends CardImpl {

    public AnimationModule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Whenever one or more +1/+1 counters are placed on a permanent you control, you may pay {1}. If you do, create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new AnimationModuleTriggeredAbility());

        // {3}, {T}: Choose a counter on target permanent or player. Give that permanent or player another counter of that kind.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AnimationModuleEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanentOrPlayer());
        this.addAbility(ability);
    }

    public AnimationModule(final AnimationModule card) {
        super(card);
    }

    @Override
    public AnimationModule copy() {
        return new AnimationModule(this);
    }
}

class AnimationModuleTriggeredAbility extends TriggeredAbilityImpl {

    AnimationModuleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new CreateTokenEffect(new ServoToken()), new GenericManaCost(1)), false);
    }

    AnimationModuleTriggeredAbility(final AnimationModuleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnimationModuleTriggeredAbility copy() {
        return new AnimationModuleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.P1P1.getName())) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return permanent != null && permanent.getControllerId().equals(this.getControllerId());
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more +1/+1 counters are placed on a permanent you control, you may pay {1}. If you do, create a 1/1 colorless Servo artifact creature token.";
    }
}

class AnimationModuleEffect extends OneShotEffect {

    AnimationModuleEffect() {
        super(Outcome.Neutral);
        this.staticText = "Choose a counter on target permanent or player. Give that permanent or player another counter of that kind";
    }

    AnimationModuleEffect(final AnimationModuleEffect effect) {
        super(effect);
    }

    @Override
    public AnimationModuleEffect copy() {
        return new AnimationModuleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                if (permanent.getCounters(game).size() > 0) {
                    if (permanent.getCounters(game).size() == 1) {
                        for (Counter counter : permanent.getCounters(game).values()) {
                            Counter newCounter = new Counter(counter.getName());
                            permanent.addCounters(newCounter, source, game);
                        }
                    }
                    else {
                        Choice choice = new ChoiceImpl(true);
                        Set<String> choices = new HashSet<>(permanent.getCounters(game).size());
                        for (Counter counter : permanent.getCounters(game).values()) {
                            choices.add(counter.getName());
                        }
                        choice.setChoices(choices);
                        choice.setMessage("Choose a counter");
                        controller.choose(Outcome.Benefit, choice, game);
                        for (Counter counter : permanent.getCounters(game).values()) {
                            if (counter.getName().equals(choice.getChoice())) {
                                Counter newCounter = new Counter(counter.getName());
                                permanent.addCounters(newCounter, source, game);
                                break;
                            }
                        }
                    }
                }
            }
            else {
                Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                if (player != null) {
                    if (player.getCounters().size() > 0) {
                        if (player.getCounters().size() == 1) {
                            for (Counter counter : player.getCounters().values()) {
                                Counter newCounter = new Counter(counter.getName());
                                player.addCounters(newCounter, game);
                            }
                        }
                        else {
                            Choice choice = new ChoiceImpl(true);
                            Set<String> choices = new HashSet<>(player.getCounters().size());
                            for (Counter counter : player.getCounters().values()) {
                                choices.add(counter.getName());
                            }
                            choice.setChoices(choices);
                            choice.setMessage("Choose a counter");
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
        return false;
    }
}

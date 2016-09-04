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
package mage.sets.timespiral;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPermanentOrSuspendedCard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class Clockspinning extends CardImpl {

    public Clockspinning(UUID ownerId) {
        super(ownerId, 53, "Clockspinning", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "TSP";

        // Buyback {3}
        this.addAbility(new BuybackAbility("{3}"));

        // Choose a counter on target permanent or suspended card. Remove that counter from that permanent or card or put another of those counters on it.
        this.getSpellAbility().addTarget(new TargetPermanentOrSuspendedCard());
        this.getSpellAbility().addEffect(new ClockspinningAddOrRemoveCounterEffect());
    }

    public Clockspinning(final Clockspinning card) {
        super(card);
    }

    @Override
    public Clockspinning copy() {
        return new Clockspinning(this);
    }
}

class ClockspinningAddOrRemoveCounterEffect extends OneShotEffect {

    ClockspinningAddOrRemoveCounterEffect() {
        super(Outcome.Removal);
        this.staticText = "Choose a counter on target permanent or suspended card. Remove that counter from that permanent or card or put another of those counters on it";
    }

    ClockspinningAddOrRemoveCounterEffect(final ClockspinningAddOrRemoveCounterEffect effect) {
        super(effect);
    }

    @Override
    public ClockspinningAddOrRemoveCounterEffect copy() {
        return new ClockspinningAddOrRemoveCounterEffect(this);
    }

    private Counter selectCounterType(Game game, Ability source, Permanent permanent) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent.getCounters(game).size() > 0) {
            String counterName = null;
            if (permanent.getCounters(game).size() > 1) {
                Choice choice = new ChoiceImpl(true);
                Set<String> choices = new HashSet<>(2);
                for (Counter counter : permanent.getCounters(game).values()) {
                    if (permanent.getCounters(game).getCount(counter.getName()) > 0) {
                        choices.add(counter.getName());
                    }
                }
                choice.setChoices(choices);
                choice.setMessage("Choose a counter type to add to " + permanent.getName());
                controller.choose(Outcome.Neutral, choice, game);
                counterName = choice.getChoice();
            } else {
                for (Counter counter : permanent.getCounters(game).values()) {
                    if (counter.getCount() > 0) {
                        counterName = counter.getName();
                    }
                }
            }
            return new Counter(counterName);
        }
        return null;
    }

    private Counter selectCounterType(Game game, Ability source, Card card) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && card.getCounters(game).size() > 0) {
            String counterName = null;
            if (card.getCounters(game).size() > 1) {
                Choice choice = new ChoiceImpl(true);
                Set<String> choices = new HashSet<>();
                for (Counter counter : card.getCounters(game).values()) {
                    if (card.getCounters(game).getCount(counter.getName()) > 0) {
                        choices.add(counter.getName());
                    }
                }
                choice.setChoices(choices);
                choice.setMessage("Choose a counter type to add to " + card.getName());
                controller.choose(Outcome.Neutral, choice, game);
                counterName = choice.getChoice();
            } else {
                for (Counter counter : card.getCounters(game).values()) {
                    if (counter.getCount() > 0) {
                        counterName = counter.getName();
                    }
                }
            }
            return new Counter(counterName);
        }
        return null;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Card card = game.getCard(source.getFirstTarget());

        if (player != null && permanent != null) {
            if (player.chooseUse(Outcome.Neutral, "Do you want to to remove a counter?", source, game)) {
                RemoveCounterTargetEffect effect = new RemoveCounterTargetEffect();
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
                effect.apply(game, source);
            } else {
                Counter counter = selectCounterType(game, source, permanent);

                if (counter != null) {
                    AddCountersTargetEffect effect = new AddCountersTargetEffect(counter);
                    effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        if (player != null && card != null) {
            if (player.chooseUse(Outcome.Neutral, "Do you want to to remove a counter?", source, game)) {
                Counter counter = selectCounterType(game, source, card);
                RemoveCounterTargetEffect effect = new RemoveCounterTargetEffect(counter);
                effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
                effect.apply(game, source);
            } else {
                Counter counter = selectCounterType(game, source, card);
                if (counter != null) {
                    AddCountersTargetEffect effect = new AddCountersTargetEffect(counter);
                    effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
                    effect.apply(game, source);
                }
            }
            return true;
        }

        return false;
    }
}

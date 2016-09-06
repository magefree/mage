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
package mage.sets.shadowmoor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class LeechBonder extends CardImpl {

    public LeechBonder(UUID ownerId) {
        super(ownerId, 43, "Leech Bonder", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Merfolk");
        this.subtype.add("Soldier");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Leech Bonder enters the battlefield with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(2))));

        // {U}, {untap}: Move a counter from target creature onto another target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new LeechBonderEffect(), new ManaCostsImpl("{U}"));
        ability.addCost(new UntapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature to remove counter from")));
        ability.addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("creature to put counter on")));
        this.addAbility(ability);

    }

    public LeechBonder(final LeechBonder card) {
        super(card);
    }

    @Override
    public LeechBonder copy() {
        return new LeechBonder(this);
    }
}

class LeechBonderEffect extends OneShotEffect {

    public LeechBonderEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Move a counter from target creature onto a second target creature";
    }

    public LeechBonderEffect(final LeechBonderEffect effect) {
        super(effect);
    }

    @Override
    public LeechBonderEffect copy() {
        return new LeechBonderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent fromPermanent = game.getPermanent(source.getFirstTarget());
        Permanent toPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (fromPermanent == null
                || toPermanent == null
                || controller == null) {
            return false;
        }
        Choice choice = new ChoiceImpl();
        Set<String> possibleChoices = new HashSet<>();
        for (String counterName : fromPermanent.getCounters(game).keySet()) {
            possibleChoices.add(counterName);
        }
        choice.setChoices(possibleChoices);
        if (controller.choose(outcome, choice, game)) {
            String chosen = choice.getChoice();
            if (fromPermanent.getCounters(game).containsKey(chosen)) {
                CounterType counterType = CounterType.findByName(chosen);
                if (counterType != null) {
                    Counter counter = counterType.createInstance();
                    fromPermanent.removeCounters(counter, game);
                    toPermanent.addCounters(counter, game);
                    return true;
                }
            }
        }
        return false;
    }
}

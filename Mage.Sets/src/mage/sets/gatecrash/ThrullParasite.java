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
 *   FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.gatecrash;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

/**
*
* @author LevelX2
*/
public class ThrullParasite extends CardImpl<ThrullParasite> {

    public ThrullParasite(UUID ownerId) {
       super(ownerId, 81, "Thrull Parasite", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{B}");
       this.expansionSetCode = "GTC";
       this.subtype.add("Thrull");

       this.color.setBlack(true);
       this.power = new MageInt(1);
       this.toughness = new MageInt(1);

       // Extort
       this.addAbility(new ExtortAbility());
       // {tap}, Pay 2 life: Remove a counter from target nonland permanent.
       Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new RemoveCounterTargetEffect(),new TapSourceCost());
       ability.addTarget(new TargetNonlandPermanent());
       ability.addCost(new PayLifeCost(2));
       this.addAbility(ability);
    }

    public ThrullParasite(final ThrullParasite card) {
       super(card);
    }

    @Override
    public ThrullParasite copy() {
       return new ThrullParasite(this);
    }
}

class RemoveCounterTargetEffect extends OneShotEffect<RemoveCounterTargetEffect> {

    private CounterType counterTypeToRemove;

    public RemoveCounterTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "Remove a counter from target nonland permanent";
    }

    public RemoveCounterTargetEffect(CounterType counterTypeToRemove) {
        super(Outcome.Detriment);
        this.staticText = "Remove a " +  counterTypeToRemove.getName() + " counter from target nonland permanent";
        this.counterTypeToRemove = counterTypeToRemove;
    }

    public RemoveCounterTargetEffect(final RemoveCounterTargetEffect effect) {
        super(effect);
        this.counterTypeToRemove = effect.counterTypeToRemove;
    }

    @Override
    public RemoveCounterTargetEffect copy() {
        return new RemoveCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        Player controller = game.getPlayer(source.getControllerId());
        for (UUID targetId: getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                if (permanent.getCounters().size() > 0 && (counterTypeToRemove == null || permanent.getCounters().containsKey(counterTypeToRemove))) {
                    String counterName = null;
                    if (counterTypeToRemove != null) {
                        counterName = counterTypeToRemove.getName();
                    } else {
                        if (permanent.getCounters().size() > 1 && counterTypeToRemove == null) {
                            Choice choice = new ChoiceImpl(true);
                            Set<String> choices = new HashSet<String>();
                            for (Counter counter : permanent.getCounters().values()) {
                                if (permanent.getCounters().getCount(counter.getName()) > 0) {
                                   choices.add(counter.getName());
                                }
                            }
                            choice.setChoices(choices);
                            choice.setMessage("Choose a counter type to remove from " + permanent.getName());
                            controller.choose(Outcome.Detriment, choice, game);
                            counterName = choice.getChoice();
                         } else {
                            for (Counter counter : permanent.getCounters().values()) {
                                if (counter.getCount() > 0) {
                                    counterName = counter.getName();
                                }
                            }
                        }
                    }
                    if (counterName != null) {
                        permanent.removeCounters(counterName, 1, game);
                        if (permanent.getCounters().getCount(counterName) == 0 ){
                            permanent.getCounters().removeCounter(counterName);
                        }
                        result |= true;
                        game.informPlayers(new StringBuilder(controller.getName()).append(" removes a ").append(counterName).append(" counter from ").append(permanent.getName()).toString());
                    }
                }
            }
        }
        return result;
     }
}

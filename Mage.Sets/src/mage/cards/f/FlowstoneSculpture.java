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
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public class FlowstoneSculpture extends CardImpl {

    public FlowstoneSculpture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}, Discard a card: Put a +1/+1 counter on Flowstone Sculpture or Flowstone Sculpture gains flying, first strike, or trample.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FlowstoneSculptureEffect(), new ManaCostsImpl("{2}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    public FlowstoneSculpture(final FlowstoneSculpture card) {
        super(card);
    }

    @Override
    public FlowstoneSculpture copy() {
        return new FlowstoneSculpture(this);
    }
}

class FlowstoneSculptureEffect extends OneShotEffect {

    private static final HashSet<String> choices = new HashSet<>();

    static {
        choices.add("+1/+1 counter");
        choices.add("Flying");
        choices.add("First Strike");
        choices.add("Trample");
    }

    public FlowstoneSculptureEffect() {
        super(Outcome.Benefit);
        staticText = "Put a +1/+1 counter on {this} or {this} gains flying, first strike, or trample.";
    }

    public FlowstoneSculptureEffect(final FlowstoneSculptureEffect effect) {
         super(effect);
    }

    @Override
    public FlowstoneSculptureEffect copy() {
        return new FlowstoneSculptureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose ability to add");
            choice.setChoices(choices);
            while(!controller.choose(outcome, choice, game)) {
                if(controller.canRespond()) {
                    return false;
                }
           }

           String chosen = choice.getChoice();
           if(chosen.equals("+1/+1 counter")) {
               return new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
           }
           else {
               Ability gainedAbility;
               switch (chosen) {
                   case "Flying":
                       gainedAbility = FlyingAbility.getInstance();
                       break;
                   case "First strike":
                       gainedAbility = FirstStrikeAbility.getInstance();
                       break;
                   default:
                       gainedAbility = TrampleAbility.getInstance();
                       break;
               }
               game.addEffect(new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield), source);
               return true;
           }

        }
        return false;
    }
}

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
package mage.sets.fallenempires;

import java.util.HashSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LoneFox
 */
public class DwarvenArmorer extends CardImpl {

    public DwarvenArmorer(UUID ownerId) {
        super(ownerId, 104, "Dwarven Armorer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "FEM";
        this.subtype.add("Dwarf");
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {R}, {tap}, Discard a card: Put a +0/+1 counter or a +1/+0 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DwarvenArmorerEffect(), new ManaCostsImpl("{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public DwarvenArmorer(final DwarvenArmorer card) {
        super(card);
    }

    @Override
    public DwarvenArmorer copy() {
        return new DwarvenArmorer(this);
    }
}

class DwarvenArmorerEffect extends OneShotEffect {

    private static final HashSet<String> choices = new HashSet<>();

    static {
        choices.add("+0/+1");
        choices.add("+1/+0");
    }

    public DwarvenArmorerEffect() {
         super(Outcome.Benefit);
         staticText = "Put a +0/+1 counter or a +1/+0 counter on target creature.";
    }

    public DwarvenArmorerEffect(final DwarvenArmorerEffect effect) {
        super(effect);
    }

    @Override
    public DwarvenArmorerEffect copy() {
        return new DwarvenArmorerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if(controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose type of counter to add");
            choice.setChoices(choices);
            while(!controller.choose(outcome, choice, game)) {
                if(controller.canRespond()) {
                    return false;
                }
            }
            Counter counter = choice.getChoice().equals("+0/+1") ? CounterType.P0P1.createInstance() : CounterType.P1P0.createInstance();
            Effect effect = new AddCountersTargetEffect(counter);
            effect.setTargetPointer(new FixedTarget(this.getTargetPointer().getFirst(game, source)));
            return effect.apply(game, source);
        }
        return false;
    }
}

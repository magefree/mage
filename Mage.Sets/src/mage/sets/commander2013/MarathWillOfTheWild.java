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
package mage.sets.commander2013;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class MarathWillOfTheWild extends CardImpl<MarathWillOfTheWild> {

    public MarathWillOfTheWild(UUID ownerId) {
        super(ownerId, 198, "Marath, Will of the Wild", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");
        this.expansionSetCode = "C13";
        this.supertype.add("Legendary");
        this.subtype.add("Elemental");
        this.subtype.add("Beast");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Marath, Will of the Wild enters the battlefield with a number of +1/+1 counters on it equal to the amount of mana spent to cast it.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), new ManaSpentToCastCount(), true);
        effect.setText("with a number of +1/+1 counters on it equal to the amount of mana spent to cast it");
        this.addAbility(new EntersBattlefieldAbility(effect));

        // {X}, Remove X +1/+1 counters from Marath: Choose one - Put X +1/+1 counters on target creature;
        effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(0), new ManacostVariableValue());
        effect.setText("Put X +1/+1 counters on target creature");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{X}"));
        ability.addCost(new MarathWillOfTheWildRemoveCountersCost());
        ability.addTarget(new TargetCreaturePermanent(true));

        // or Marath deals X damage to target creature or player;
        Mode mode = new Mode();
        mode.getEffects().add(new DamageTargetEffect(new ManacostVariableValue()));
        mode.getTargets().add(new TargetCreatureOrPlayer(true));
        ability.addMode(mode);

        // or put an X/X green Elemental creature token onto the battlefield.
        mode = new Mode();
        mode.getEffects().add(new MarathWillOfTheWildCreateTokenEffect());
        ability.addMode(mode);

        // X can't be 0.
        for (VariableCost cost: ability.getManaCosts().getVariableCosts()) {
            if (cost instanceof VariableManaCost) {
                ((VariableManaCost) cost).setMinX(1);
                break;
            }
        }
        this.addAbility(ability);
    }

    @Override
    public void adjustChoices(Ability ability, Game game) {
        if (ability instanceof SimpleActivatedAbility && ability.getModes().size() == 3) {
            Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
            if (sourcePermanent != null) {
                int amount = sourcePermanent.getCounters().getCount(CounterType.P1P1);
                if (amount > 0) {
                    for (VariableCost cost: ability.getManaCostsToPay().getVariableCosts()) {
                        if (cost instanceof VariableManaCost) {
                            ((VariableManaCost) cost).setMaxX(amount);
                            break;
                        }
                    }
                }
            }
        }

    }

    public MarathWillOfTheWild(final MarathWillOfTheWild card) {
        super(card);
    }

    @Override
    public MarathWillOfTheWild copy() {
        return new MarathWillOfTheWild(this);
    }
}


class ManaSpentToCastCount  implements DynamicValue{

    public ManaSpentToCastCount(){
    }

    @Override
    public int calculate(Game game, Ability source) {
        int count = 0;
        if (!game.getStack().isEmpty()) {
            StackObject spell = game.getStack().getFirst();
            if (spell != null &&  spell instanceof Spell && ((Spell)spell).getSourceId().equals(source.getSourceId())) {
                count = ((Spell)spell).getSpellAbility().getManaCostsToPay().convertedManaCost();
            }
        }
        return count;
    }

    @Override
    public DynamicValue copy() {
        return new ManaSpentToCastCount();
    }


    @Override
    public String getMessage() {
        return "the amount of mana spent to cast it";
    }

}

class MarathWillOfTheWildCreateTokenEffect extends OneShotEffect<MarathWillOfTheWildCreateTokenEffect> {

    public MarathWillOfTheWildCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put an X/X green Elemental creature token onto the battlefield";
    }

    public MarathWillOfTheWildCreateTokenEffect(final MarathWillOfTheWildCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public MarathWillOfTheWildCreateTokenEffect copy() {
        return new MarathWillOfTheWildCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int amount = new ManacostVariableValue().calculate(game, source);
            Token token = new MarathWillOfTheWildElementalToken();
            token.getPower().initValue(amount);
            token.getToughness().initValue(amount);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }
}

class MarathWillOfTheWildElementalToken extends Token {
    public MarathWillOfTheWildElementalToken() {
        super("Elemental", "X/X green Elemental creature token");
        cardType.add(CardType.CREATURE);
        subtype.add("Elemental");
        color.setGreen(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }
}

class MarathWillOfTheWildRemoveCountersCost extends CostImpl<MarathWillOfTheWildRemoveCountersCost> {

    public MarathWillOfTheWildRemoveCountersCost() {
        this.text = "Remove X +1/+1 counters from Marath";

    }

    public MarathWillOfTheWildRemoveCountersCost(MarathWillOfTheWildRemoveCountersCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null && permanent.getCounters().getCount(CounterType.P1P1) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        int amount = new ManacostVariableValue().calculate(game, ability);
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null && permanent.getCounters().getCount(CounterType.P1P1) >= amount) {
            permanent.removeCounters(CounterType.P1P1.getName(), amount, game);
            this.paid = true;
        }
        return paid;
    }

    @Override
    public MarathWillOfTheWildRemoveCountersCost copy() {
        return new MarathWillOfTheWildRemoveCountersCost(this);
    }
}

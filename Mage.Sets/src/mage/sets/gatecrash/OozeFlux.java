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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class OozeFlux extends CardImpl<OozeFlux> {

    public OozeFlux(UUID ownerId) {
        super(ownerId, 128, "Ooze Flux", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.expansionSetCode = "GTC";

        this.color.setGreen(true);

        // {1}{G}, Remove one or more +1/+1 counters from among creatures you control: Put an X/X green Ooze creature token onto the battlefield, where X is the number of +1/+1 counters removed this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new OozeFluxCreateTokenEffect(new OozeToken()),new ManaCostsImpl("{1}{G}"));
        ability.addCost(new RemoveVariableCountersTargetCost(
                new TargetControlledCreaturePermanent(1,Integer.MAX_VALUE,new FilterControlledCreaturePermanent(), true), CounterType.P1P1));
        this.addAbility(ability);
    }

    public OozeFlux(final OozeFlux card) {
        super(card);
    }

    @Override
    public OozeFlux copy() {
        return new OozeFlux(this);
    }
}

class OozeFluxCreateTokenEffect extends OneShotEffect<OozeFluxCreateTokenEffect> {

    private Token token;

    public OozeFluxCreateTokenEffect(Token token) {
        super(Outcome.PutCreatureInPlay);
        this.token = token;
        staticText = "Put an X/X green Ooze creature token onto the battlefield, where X is the number of +1/+1 counters removed this way";
    }

    public OozeFluxCreateTokenEffect(final OozeFluxCreateTokenEffect effect) {
        super(effect);
        this.token = effect.token.copy();
    }

    @Override
    public OozeFluxCreateTokenEffect copy() {
        return new OozeFluxCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof RemoveVariableCountersTargetCost) {
                xValue = ((RemoveVariableCountersTargetCost) cost).getAmount();
                break;
            }
        }
        Token tokenCopy = token.copy();
        tokenCopy.getAbilities().newId();
        tokenCopy.getPower().setValue(xValue);
        tokenCopy.getToughness().setValue(xValue);
        tokenCopy.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        return true;
    }
}

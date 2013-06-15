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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public class FalkenrathAristocrat extends CardImpl<FalkenrathAristocrat> {

    public FalkenrathAristocrat(UUID ownerId) {
        super(ownerId, 138, "Falkenrath Aristocrat", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Vampire");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        // Sacrifice a creature: Falkenrath Aristocrat is indestructible this turn.
        // If the sacrificed creature was a Human, put a +1/+1 counter on Falkenrath Aristocrat.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        ability.addEffect(new FalkenrathAristocratEffect());
        this.addAbility(ability);
    }

    public FalkenrathAristocrat(final FalkenrathAristocrat card) {
        super(card);
    }

    @Override
    public FalkenrathAristocrat copy() {
        return new FalkenrathAristocrat(this);
    }
}

class FalkenrathAristocratEffect extends OneShotEffect<FalkenrathAristocratEffect> {

    public FalkenrathAristocratEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "If the sacrificed creature was a Human, put a +1/+1 counter on {this}";
    }

    public FalkenrathAristocratEffect(final FalkenrathAristocratEffect effect) {
        super(effect);
    }

    @Override
    public FalkenrathAristocratEffect copy() {
        return new FalkenrathAristocratEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                Permanent sacrificedCreature = ((SacrificeTargetCost) cost).getPermanents().get(0);
                Permanent sourceCreature = game.getPermanent(source.getSourceId());
                if (sacrificedCreature.hasSubtype("Human") && sourceCreature != null) {
                    sourceCreature.addCounters(CounterType.P1P1.createInstance(), game);
                    return true;
                }
            }
        }
        return false;
    }
}

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
package mage.sets.conflux;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Loki
 */
public class ApocalypseHydra extends CardImpl<ApocalypseHydra> {

    public ApocalypseHydra(UUID ownerId) {
        super(ownerId, 98, "Apocalypse Hydra", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{X}{R}{G}");
        this.expansionSetCode = "CON";
        this.subtype.add("Hydra");

        this.color.setRed(true);
        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Apocalypse Hydra enters the battlefield with X +1/+1 counters on it. If X is 5 or more, it enters the battlefield with an additional X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new ApocalypseHydraEffect(), ""));
        // {1}{R}, Remove a +1/+1 counter from Apocalypse Hydra: Apocalypse Hydra deals 1 damage to target creature or player.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl("{1}{R}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public ApocalypseHydra(final ApocalypseHydra card) {
        super(card);
    }

    @Override
    public ApocalypseHydra copy() {
        return new ApocalypseHydra(this);
    }
}

class ApocalypseHydraEffect extends OneShotEffect<ApocalypseHydraEffect> {
    ApocalypseHydraEffect() {
        super(Constants.Outcome.BoostCreature);
        staticText = "with X +1/+1 counters on it. If X is 5 or more, it enters the battlefield with an additional X +1/+1 counters on it";
    }

    ApocalypseHydraEffect(final ApocalypseHydraEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            if (amount < 5) {
                p.addCounters(CounterType.P1P1.createInstance(amount), game);
            } else {
                p.addCounters(CounterType.P1P1.createInstance(amount * 2), game);
            }
            return true;
        }
        return true;
    }

    @Override
    public ApocalypseHydraEffect copy() {
        return new ApocalypseHydraEffect(this);
    }
}

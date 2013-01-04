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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class SimicFluxmage extends CardImpl<SimicFluxmage> {

    public SimicFluxmage(UUID ownerId) {
        super(ownerId, 49, "Simic Fluxmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Evolve (Whenever a creature enters the battlefield under your control, if that creature has greater power or toughness than this creature, put a +1/+1 counter on this creature.)
        this.addAbility(new EvolveAbility());

        // 1{U}, {T}: Move a +1/+1 counter from Simic Fluxmage onto target creature.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new MoveCounterFromSourceToTargetEffect(),new ManaCostsImpl("{1}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public SimicFluxmage(final SimicFluxmage card) {
        super(card);
    }

    @Override
    public SimicFluxmage copy() {
        return new SimicFluxmage(this);
    }
}

class MoveCounterFromSourceToTargetEffect extends OneShotEffect<MoveCounterFromSourceToTargetEffect> {

    public MoveCounterFromSourceToTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "Move a +1/+1 counter from {this} onto target creature";
    }

    public MoveCounterFromSourceToTargetEffect(final MoveCounterFromSourceToTargetEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterFromSourceToTargetEffect copy() {
        return new MoveCounterFromSourceToTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && sourcePermanent.getCounters().getCount(CounterType.P1P1) > 0) {
            Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (targetPermanent != null) {
                sourcePermanent.getCounters().removeCounter(CounterType.P1P1, 1);
                targetPermanent.addCounters(CounterType.P1P1.createInstance(), game);
                return true;
            }
        }
        return false;
    }
}

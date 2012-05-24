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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public class ContagionEngine extends CardImpl<ContagionEngine> {

    public ContagionEngine (UUID ownerId) {
        super(ownerId, 145, "Contagion Engine", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{6}");
        this.expansionSetCode = "SOM";
        Ability ability = new EntersBattlefieldTriggeredAbility(new ContagionEngineEffect());
        ability.addTarget(new TargetPlayer());
		this.addAbility(ability);
		ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ProliferateEffect(), new GenericManaCost(4));
		ability.addCost(new TapSourceCost());
		ability.addEffect(new ProliferateEffect());
		this.addAbility(ability);
    }

    public ContagionEngine (final ContagionEngine card) {
        super(card);
    }

    @Override
    public ContagionEngine copy() {
        return new ContagionEngine(this);
    }

}

class ContagionEngineEffect extends OneShotEffect<ContagionEngineEffect> {
	ContagionEngineEffect() {
		super(Constants.Outcome.UnboostCreature);
		staticText = "put a -1/-1 counter on each creature target player controls";
	}

	ContagionEngineEffect(final ContagionEngineEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player target = game.getPlayer(source.getFirstTarget());
		if (target != null) {
			for (Permanent p : game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), target.getId(), game)) {
				p.addCounters(CounterType.M1M1.createInstance(), game);
			}
			return true;
		}
		return false;
	}

	@Override
	public ContagionEngineEffect copy() {
		return new ContagionEngineEffect(this);
	}

}
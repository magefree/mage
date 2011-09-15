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

package mage.sets.mirrodinbesieged;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Viserion
 */
public class PistusStrike extends CardImpl<PistusStrike> {

	private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

	static {
		filter.getAbilities().add(FlyingAbility.getInstance());
	}

	public PistusStrike(UUID ownerId) {
		super(ownerId, 86, "Pistus Strike", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{G}");
		this.expansionSetCode = "MBS";
		this.color.setGreen(true);
		
		this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
		this.getSpellAbility().addEffect(new DestroyTargetEffect());
		this.getSpellAbility().addEffect(new PoisonControllerTargetCreatureEffect());
	}

	public PistusStrike(final PistusStrike card) {
		super(card);
	}

	@Override
	public PistusStrike copy() {
		return new PistusStrike(this);
	}
}

class PoisonControllerTargetCreatureEffect extends OneShotEffect<PoisonControllerTargetCreatureEffect> {

	public PoisonControllerTargetCreatureEffect() {
		super(Outcome.Damage);
        staticText = "Its controller gets a poison counter";
	}

	public PoisonControllerTargetCreatureEffect(final PoisonControllerTargetCreatureEffect effect) {
		super(effect);
	}

	@Override
	public PoisonControllerTargetCreatureEffect copy() {
		return new PoisonControllerTargetCreatureEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent p = game.getBattlefield().getPermanent(source.getFirstTarget());
        if (p == null) {
            p = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }
        if (p != null) {
            Player player = game.getPlayer(p.getControllerId());
            if (player != null) {
    			player.addCounters(CounterType.POISON.createInstance(), game);
            }
        }
		return false;
	}
}

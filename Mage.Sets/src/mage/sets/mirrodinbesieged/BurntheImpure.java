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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author ayratn
 */
public class BurntheImpure extends CardImpl<BurntheImpure> {

    public BurntheImpure (UUID ownerId) {
        super(ownerId, 59, "Burn the Impure", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "MBS";
    	this.color.setRed(true);
		this.getSpellAbility().addTarget(new TargetCreaturePermanent());
		this.getSpellAbility().addEffect(new BurntheImpureEffect(3));
    }

    public BurntheImpure (final BurntheImpure card) {
        super(card);
    }

    @Override
    public BurntheImpure copy() {
        return new BurntheImpure(this);
    }

	public class BurntheImpureEffect extends OneShotEffect<BurntheImpureEffect> {

	protected int amount;

	public BurntheImpureEffect(int amount) {
		super(Constants.Outcome.Damage);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public BurntheImpureEffect(final BurntheImpureEffect effect) {
		super(effect);
		this.amount = effect.amount;
	}

	@Override
	public BurntheImpureEffect copy() {
		return new BurntheImpureEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			permanent.damage(amount, source.getId(), game, true, false);
			if (permanent.getAbilities().contains(InfectAbility.getInstance())) {
				Player controller = game.getPlayer(permanent.getControllerId());
				if (controller != null) {
					controller.damage(amount, source.getSourceId(), game, true, false);
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "Burn the Impure deals 3 damage to target creature. If that creature has infect, Burn the Impure deals 3 damage to that creature's controller.";
	}

}

}

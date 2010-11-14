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

package mage.sets.tenth;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Condemn extends CardImpl<Condemn> {

	public Condemn(UUID ownerId) {
		super(ownerId, 13, "Condemn", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}");
		this.expansionSetCode = "10E";
		this.color.setWhite(true);
		this.getSpellAbility().addTarget(new TargetAttackingCreature());
		this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(false));
		this.getSpellAbility().addEffect(new CondemnEffect());

	}

	public Condemn(final Condemn card) {
		super(card);
	}

	@Override
	public Condemn copy() {
		return new Condemn(this);
	}

	@Override
	public String getArt() {
		return "94551_typ_reg_sty_010.jpg";
	}

}

class CondemnEffect extends OneShotEffect<CondemnEffect> {

	public CondemnEffect() {
		super(Outcome.GainLife);
	}

	public CondemnEffect(final CondemnEffect effect) {
		super(effect);
	}

	@Override
	public CondemnEffect copy() {
		return new CondemnEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			Player player = game.getPlayer(permanent.getControllerId());
			if (player != null) {
				player.gainLife(permanent.getToughness().getValue(), game);
			}
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "Its controller gains life equal to its toughness";
	}

}

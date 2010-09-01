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

package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.sets.Magic2010;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class TimeWarp extends CardImpl<TimeWarp> {

	public TimeWarp(UUID ownerId) {
		super(ownerId, "Time Warp", new CardType[]{CardType.SORCERY}, "{3}{U}{U}");
		this.expansionSetId = Magic2010.getInstance().getId();
		this.color.setBlue(true);
		this.getSpellAbility().addTarget(new TargetPlayer());
		this.getSpellAbility().addEffect(new TimeWarpEffect());
	}

	public TimeWarp(final TimeWarp card) {
		super(card);
	}

	@Override
	public TimeWarp copy() {
		return new TimeWarp(this);
	}

	@Override
	public String getArt() {
		return "122160_typ_reg_sty_010.jpg";
	}

}

class TimeWarpEffect extends OneShotEffect<TimeWarpEffect> {

	public TimeWarpEffect() {
		super(Outcome.ExtraTurn);
	}

	public TimeWarpEffect(final TimeWarpEffect effect) {
		super(effect);
	}

	@Override
	public TimeWarpEffect copy() {
		return new TimeWarpEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		game.getState().getTurnMods().add(new TurnMod(source.getFirstTarget(), false));
		return true;
	}

	@Override
	public String getText(Ability source) {
		return "Target player takes an extra turn after this one.";
	}
}
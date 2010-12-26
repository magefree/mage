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

package mage.abilities.decorator;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.game.Game;

/**
 * Adds condition to {@link OneShotEffect}. Acts as decorator.
 * 
 * @author maurer.it_at_gmail.com
 */
public class ConditionalOneShotEffect extends OneShotEffect<ConditionalOneShotEffect> {

	private OneShotEffect effect;
	private Condition condition;
	private String text;

	public ConditionalOneShotEffect ( OneShotEffect effect, Condition condition, String text ) {
		super(effect.getOutcome());
		this.effect = effect;
		this.condition = condition;
		this.text = text;
	}

	public ConditionalOneShotEffect ( ConditionalOneShotEffect effect ) {
		this(effect, effect.condition, effect.text);
	}

	@Override
	public boolean apply ( Game game, Ability source ) {
		if ( condition.apply(game, source) ) {
			return effect.apply(game, source);
		}
		return false;
	}

	@Override
	public ConditionalOneShotEffect copy ( ) {
		return new ConditionalOneShotEffect ( this );
	}

	@Override
	public String getText(Ability source) {
		return this.text;
	}
}

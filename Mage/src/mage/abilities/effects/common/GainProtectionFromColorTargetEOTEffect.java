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

package mage.abilities.effects.common;

import mage.Constants.Duration;
import mage.abilities.keyword.ProtectionAbility;
import mage.choices.ChoiceColor;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GainProtectionFromColorTargetEOTEffect extends GainAbilityTargetEffect {

	FilterCard protectionFilter;

	public GainProtectionFromColorTargetEOTEffect() {
		super(new ProtectionAbility(new FilterCard()), Duration.EndOfTurn);
		protectionFilter = (FilterCard)((ProtectionAbility)ability).getFilter();
		protectionFilter.setUseColor(true);
	}

	@Override
	public boolean apply(Game game) {
		ChoiceColor choice = (ChoiceColor) this.source.getChoices().get(0);
		protectionFilter.setColor(choice.getColor());
		protectionFilter.setMessage(choice.getChoice());
		Permanent creature = game.getPermanent(source.getFirstTarget());
		creature.addAbility(ability);
		return true;
	}

	@Override
	public String getText() {
		return "target creature you control gains protection from the color of your choice until end of turn";
	}

}

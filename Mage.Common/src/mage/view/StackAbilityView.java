/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.view;

import java.util.ArrayList;

import mage.game.stack.StackAbility;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StackAbilityView extends CardView {

	private String sourceName;
	private CardView sourceCard;

	public StackAbilityView(StackAbility ability, String sourceName, CardView sourceCard) {
		this.id = ability.getId();
		this.name = "Ability";
		this.sourceName = sourceName;
		this.sourceCard = sourceCard;
		this.rules = new ArrayList<String>();
		rules.add(formatRule(ability.getRule()));
		this.power = ability.getPower().toString();
		this.toughness = ability.getToughness().toString();
		this.loyalty = ability.getLoyalty().toString();
		this.cardTypes = ability.getCardType();
		this.subTypes = ability.getSubtype();
		this.superTypes = ability.getSupertype();
		this.color = ability.getColor();
		this.manaCost = ability.getManaCost().getSymbols();
		this.art = "";
		setTargets(ability.getTargets());
	}

	@Override
	protected String formatRule(String rule) {
		String newRule = rule.replace("{this}", this.sourceName);
		newRule.replace("{source}", this.sourceName);
		return newRule;
	}
	
	public CardView getSourceCard() {
		return this.sourceCard;
	}
}

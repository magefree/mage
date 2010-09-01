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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.ObjectColor;
import mage.cards.Card;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardView implements Serializable {

	protected UUID id;
	protected String name;
	protected List<String> rules;
	protected String power;
	protected String toughness;
	protected String loyalty;
	protected List<CardType> cardTypes;
	protected List<String> subTypes;
	protected List<String> superTypes;
	protected ObjectColor color;
	protected List<String> manaCost;
	protected String art;

	public CardView(Card card) {
		this.id = card.getId();
		this.name = card.getName();
		this.rules = formatRules(card.getRules());
		if (card instanceof Permanent) {
			this.power = Integer.toString(card.getPower().getValue());
			this.toughness = Integer.toString(card.getToughness().getValue());
			this.loyalty = Integer.toString(card.getLoyalty().getValue());
		} else {
			this.power = card.getPower().toString();
			this.toughness = card.getToughness().toString();
			this.loyalty = card.getLoyalty().toString();
		}
		this.cardTypes = card.getCardType();
		this.subTypes = card.getSubtype();
		this.superTypes = card.getSupertype();
		this.color = card.getColor();
		this.manaCost = card.getManaCost().getSymbols();
		this.art = card.getArt();
	}

	protected CardView() {

	}

	protected List<String> formatRules(List<String> rules) {
		List<String> newRules = new ArrayList<String>();
		for (String rule: rules) {
			newRules.add(formatRule(rule));
		}
		return newRules;
	}

	protected String formatRule(String rule) {
		String replace = rule.replace("{this}", this.name);
		replace = replace.replace("{source}", this.name);
		return replace;
	}

	public String getName() {
		return name;
	}

	public List<String> getRules() {
		return rules;
	}

	public String getPower() {
		return power;
	}

	public String getToughness() {
		return toughness;
	}

	public String getLoyalty() {
		return loyalty;
	}

	public List<CardType> getCardTypes() {
		return cardTypes;
	}

	public List<String> getSubTypes() {
		return subTypes;
	}

	public List<String> getSuperTypes() {
		return superTypes;
	}

	public ObjectColor getColor() {
		return color;
	}

	public List<String> getManaCost() {
		return manaCost;
	}

	public String getArt() {
		return art;
	}

	public UUID getId() {
		return id;
	}
}

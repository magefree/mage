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

import mage.ObjectColor;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.cards.Card;
import mage.counters.Counters;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.target.Targets;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CardView implements Serializable {
    private static final long serialVersionUID = 1L;

	protected UUID id;
	protected UUID parentId;
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
	protected int convertedManaCost;
	protected String art;
	protected Rarity rarity;
	protected String expansionSetCode;
	protected int cardNumber;
	protected boolean isAbility;
	protected CardView ability;

	public List<UUID> targets;
	
	public CardView(Card card) {
		this.id = card.getId();
		this.name = card.getName();
		this.rules = card.getRules();
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
		this.convertedManaCost = card.getManaCost().convertedManaCost();
		if (card instanceof PermanentToken) {
			this.art = "";
			this.rarity = Rarity.NA;
			this.expansionSetCode = "";
		}
		else {
			this.art = card.getArt();
			this.rarity = card.getRarity();
			this.expansionSetCode = card.getExpansionSetCode();
		}
		this.cardNumber = card.getCardNumber();
		
		if (card instanceof Spell) {
			Spell<?> spell = (Spell<?>)card;
			if (spell.getSpellAbility().getTargets().size() > 0) {
				setTargets(spell.getSpellAbility().getTargets());
			}
		}
	}

	protected CardView() {

	}

	CardView(Token token) {
		this.id = token.getId();
		this.name = token.getName();
		this.rules = token.getAbilities().getRules(this.name);
		this.power = token.getPower().toString();
		this.toughness = token.getToughness().toString();
		this.loyalty = token.getLoyalty().toString();
		this.cardTypes = token.getCardType();
		this.subTypes = token.getSubtype();
		this.superTypes = token.getSupertype();
		this.color = token.getColor();
		this.manaCost = token.getManaCost().getSymbols();
		this.art = "";
		this.rarity = Rarity.NA;
		this.expansionSetCode = "";
	}

	protected void setTargets(Targets targets) {
		for (Target target : targets) {
			if (target.isChosen()) {
				for (UUID targetUUID : target.getTargets()) {
					if (this.targets == null) this.targets = new ArrayList<UUID>();
					this.targets.add(targetUUID);
				}
			}	
		}
	}
	
//	protected List<String> formatRules(List<String> rules) {
//		List<String> newRules = new ArrayList<String>();
//		for (String rule: rules) {
//			newRules.add(formatRule(rule));
//		}
//		return newRules;
//	}
//
//	protected String formatRule(String rule) {
//		String replace = rule.replace("{this}", this.name);
//		replace = replace.replace("{source}", this.name);
//		return replace;
//	}

	public String getName() {
		return name;
	}

	public List<String> getRules() {
		return rules;
	}
	
	public void overrideRules(List<String> rules) {
		this.rules = rules;
	}

	public void setIsAbility(boolean isAbility) {
		this.isAbility = isAbility;
	}
	
	public boolean isAbility() {
		return isAbility;
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

	public int getConvertedManaCost() {
		return convertedManaCost;
	}

	public String getArt() {
		return art;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public String getExpansionSetCode() {
		return expansionSetCode;
	}

	public UUID getId() {
		return id;
	}
	
	public int getCardNumber() {
		return cardNumber;
	}
	
	/**
	 * Returns UUIDs for targets.
	 * Can be null if there is no target selected.
	 * @return
	 */
	public List<UUID> getTargets() {
		return targets;
	}
	
	public void overrideTargets(List<UUID> newTargets) {
		this.targets = newTargets;
	}
	
	public void overrideId(UUID id) {
		if (parentId == null) {
			parentId = this.id;
		}
		this.id = id;
	}
	
	public UUID getParentId() {
		if (parentId != null) {
			return parentId;
		}
		return id;
	}
	
	public void setAbility(CardView ability) {
		this.ability = ability;
	}
	
	public CardView getAbility() {
		return this.ability;
	}
	
	@Override
	public String toString() {
		return getName() + " [" + getId() + "]";
	}
}

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

package mage.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterObject<E extends MageObject, T extends FilterObject<E, T>> extends FilterImpl<E, T> implements Filter<E> {
	protected Abilities<Ability> abilities;
	protected boolean notAbilities;
	protected List<CardType> cardType = new ArrayList<CardType>();
	protected ComparisonScope scopeCardType = ComparisonScope.All;
	protected List<CardType> notCardTypeList = new ArrayList<CardType>();
	protected ComparisonScope notScopeCardType = ComparisonScope.Any;
	protected boolean notCardType;
	protected boolean colorless;
	protected boolean useColorless;
	protected boolean useColor;
	protected ObjectColor color;
	protected ComparisonScope scopeColor = ComparisonScope.Any;
	protected boolean notColor;
	protected List<String> name = new ArrayList<String>();
	protected boolean notName;
	protected List<String> subtype = new ArrayList<String>();
	protected ComparisonScope scopeSubtype = ComparisonScope.All;
	protected boolean notSubtype;
	protected List<String> supertype = new ArrayList<String>();
	protected ComparisonScope scopeSupertype = ComparisonScope.All;
	protected boolean notSupertype;
	protected int convertedManaCost;
	protected ComparisonType convertedManaCostComparison;
	protected int power;
	protected ComparisonType powerComparison;
	protected int toughness;
	protected ComparisonType toughnessComparison;
	protected UUID id;
	protected boolean notId;

	/**
	 * Indicates that filter shouldn't match the source.
	 */
	protected boolean another;

	@Override
	public FilterObject<E, T> copy() {
		return new FilterObject<E, T>(this);
	}

	public FilterObject(String name) {
		super(name);
		abilities = new AbilitiesImpl<Ability>();
		color = new ObjectColor();
	}

	public FilterObject(FilterObject filter) {
		super(filter);
		this.abilities = filter.abilities.copy();
		this.notAbilities = filter.notAbilities;
		for (CardType cType: (List<CardType>)filter.cardType) {
			this.cardType.add(cType);
		}
		for (CardType cType: (List<CardType>)filter.notCardTypeList) {
			this.notCardTypeList.add(cType);
		}
		this.scopeCardType = filter.scopeCardType;
		this.notCardType = filter.notCardType;
		this.notScopeCardType = filter.notScopeCardType;
		this.colorless = filter.colorless;
		this.useColorless = filter.useColorless;
		this.useColor = filter.useColor;
		this.color = filter.color.copy();
		this.scopeColor = filter.scopeColor;
		this.notColor = filter.notColor;
		for (String fName: (List<String>)filter.name) {
			this.name.add(fName);
		}
		this.notName = filter.notName;
		for (String fSubtype: (List<String>)filter.subtype) {
			this.subtype.add(fSubtype);
		}
		this.scopeSubtype = filter.scopeSubtype;
		this.notSubtype = filter.notSubtype;
		for (String fSupertype: (List<String>)filter.supertype) {
			this.supertype.add(fSupertype);
		}
		this.scopeSupertype = filter.scopeSupertype;
		this.notSupertype = filter.notSupertype;
		this.convertedManaCost = filter.convertedManaCost;
		this.convertedManaCostComparison = filter.convertedManaCostComparison;
		this.power = filter.power;
		this.powerComparison = filter.powerComparison;
		this.toughness = filter.toughness;
		this.toughnessComparison = filter.toughnessComparison;
		this.id = filter.id;
		this.notId = filter.notId;
		this.another = filter.another;
	}

	@Override
	public boolean match(E object) {

		if (id != null) {
			if (object.getId().equals(id) == notId)
				return notFilter;
		}

		if (name.size() > 0) {
			if (name.contains(object.getName()) == notName)
				return notFilter;
		}

		if (useColor) {
			if (scopeColor == ComparisonScope.All) {
				if (object.getColor().equals(color) == notColor) {
					return notFilter;
				}
			}
			else if (object.getColor().contains(color) == notColor) {
				if (useColorless && colorless) { //need to treat colorless like a color in this case
					if (object.getColor().isColorless() != colorless) {
						return notFilter;
					}
				}
				else {
					return notFilter;
				}
			}
		}
		else if (useColorless && object.getColor().isColorless() != colorless) {
			return notFilter;
		}

		if (cardType.size() > 0) {
			if (!compCardType.compare(cardType, object.getCardType(), scopeCardType, notCardType))
				return notFilter;
		}

		if (notCardTypeList.size() > 0) {
			if (compCardType.compare(notCardTypeList, object.getCardType(), notScopeCardType, false))
				return notFilter;
		}
		
		if (subtype.size() > 0) {
			if (!compString.compare(subtype, object.getSubtype(), scopeSubtype, notSubtype))
				return notFilter;
		}

		if (supertype.size() > 0) {
			if (!compString.compare(supertype, object.getSupertype(), scopeSupertype, notSupertype))
				return notFilter;
		}

		if (abilities.size() > 0 && object.getAbilities().containsAll(abilities) == notAbilities) {
			return notFilter;
		}

		if (convertedManaCostComparison != null) {
			if (!compareInts(object.getManaCost().convertedManaCost(), convertedManaCost, convertedManaCostComparison))
				return notFilter;
		}

		if (powerComparison != null) {
			if (!compareInts(object.getPower().getValue(), power, powerComparison))
				return notFilter;
		}

		if (toughnessComparison != null) {
			if (!compareInts(object.getToughness().getValue(), toughness, toughnessComparison))
				return notFilter;
		}

		return !notFilter;
	}

	public Abilities getAbilities() {
		return this.abilities;
	}

	public void setNotAbilities(boolean notAbilities) {
		this.notAbilities = notAbilities;
	}

	public List<CardType> getCardType() {
		return this.cardType;
	}
	
	public List<CardType> getNotCardType() {
		return this.notCardTypeList;
	}

	public void setScopeCardType(ComparisonScope scopeCardType) {
		this.scopeCardType = scopeCardType;
	}
	
	public void setNotScopeCardType(ComparisonScope notScopeCardType) {
		this.notScopeCardType = notScopeCardType;
	}

	public void setNotCardType(boolean notCardTypeList) {
		this.notCardType = notCardTypeList;
	}

	public void setColor(ObjectColor color) {
		this.color = color;
	}

	public ObjectColor getColor() {
		return this.color;
	}

	public void setScopeColor(ComparisonScope scopeColor) {
		this.scopeColor = scopeColor;
	}

	public void setNotColor(boolean notColor) {
		this.notColor = notColor;
	}

	public List<String> getName() {
		return this.name;
	}

	public void setNotName(boolean notName) {
		this.notName = notName;
	}

	public List<String> getSubtype() {
		return this.subtype;
	}

	public void setScopeSubtype(ComparisonScope scopeSubtype) {
		this.scopeSubtype = scopeSubtype;
	}

	public void setNotSubtype(boolean notSubtype) {
		this.notSubtype = notSubtype;
	}

	public List<String> getSupertype() {
		return this.supertype;
	}

	public void setScopeSupertype(ComparisonScope scopeSupertype) {
		this.scopeSupertype = scopeSupertype;
	}

	public void setNotSupertype(boolean notSupertype) {
		this.notSupertype = notSupertype;
	}

	public void setConvertedManaCost(int convertedManaCost) {
		this.convertedManaCost = convertedManaCost;
	}

	public void setConvertedManaCostComparison(ComparisonType convertedManaCostComparison) {
		this.convertedManaCostComparison = convertedManaCostComparison;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public void setPowerComparison(ComparisonType powerComparison) {
		this.powerComparison = powerComparison;
	}

	public void setToughness(int toughness) {
		this.toughness = toughness;
	}

	public void setToughnessComparison(ComparisonType toughnessComparison) {
		this.toughnessComparison = toughnessComparison;
	}

	public void setUseColor(boolean useColor) {
		this.useColor = useColor;
	}

	public void setColorless(boolean colorless) {
		this.colorless = colorless;
	}

	public void setUseColorless(boolean useColorless) {
		this.useColorless = useColorless;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setNotId(boolean notId) {
		this.notId = notId;
	}

	public boolean isAnother() {
		return another;
	}

	public void setAnother(boolean another) {
		this.another = another;
	}
}

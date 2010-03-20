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

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class FilterObject<T extends MageObject> extends FilterImpl<T> implements Filter<T> {
	protected Abilities abilities = new AbilitiesImpl();
	protected boolean notAbilities = false;
	protected List<CardType> cardType = new ArrayList<CardType>();
	protected ComparisonScope scopeCardType = ComparisonScope.All;
	protected boolean notCardType = false;
	protected boolean colorless = false;
	protected boolean useColorless = false;
	protected boolean useColor = false;
	protected ObjectColor color = new ObjectColor();
	protected ComparisonScope scopeColor = ComparisonScope.All;
	protected boolean notColor = false;
	protected List<String> name = new ArrayList<String>();
	protected boolean notName = false;
	protected List<String> subtype = new ArrayList<String>();
	protected ComparisonScope scopeSubtype = ComparisonScope.All;
	protected boolean notSubtype = false;
	protected List<String> supertype = new ArrayList<String>();
	protected ComparisonScope scopeSupertype = ComparisonScope.All;
	protected boolean notSupertype = false;
	protected int convertedManaCost = 0;
	protected ComparisonType convertedManaCostComparison;
	protected int power = 0;
	protected ComparisonType powerComparison;
	protected int toughness = 0;
	protected ComparisonType toughnessComparison;
	protected UUID id = null;
	protected boolean notId;

	public FilterObject(String name) {
		super(name);
	}

	@Override
	public boolean match(T object) {

		if (id != null) {
			if (object.getId().equals(id) == notId)
				return false;
		}

		if (name.size() > 0) {
			if (name.contains(object.getName()) == notName)
				return false;
		}

		if (useColor) {
			if (scopeColor == ComparisonScope.All) {
				if (object.getColor().equals(color) == notColor) {
					return false;
				}
			}
			else if (object.getColor().contains(color) == notColor) {
				if (useColorless && colorless) { //need to treat colorless like a color in this case
					if (object.getColor().isColorless() != colorless) {
						return false;
					}
				}
				else {
					return false;
				}
			}
		}
		else if (useColorless && object.getColor().isColorless() != colorless) {
			return false;
		}

		if (cardType.size() > 0) {
			if (!compCardType.compare(cardType, object.getCardType(), scopeCardType, notCardType))
				return false;
		}

		if (subtype.size() > 0) {
			if (!compString.compare(subtype, object.getSubtype(), scopeSubtype, notSubtype))
				return false;
		}

		if (supertype.size() > 0) {
			if (!compString.compare(supertype, object.getSubtype(), scopeSupertype, notSupertype))
				return false;
		}

		if (abilities.size() > 0 && object.getAbilities().containsAll(abilities) == notAbilities) {
			return false;
		}

		if (convertedManaCostComparison != null) {
			if (!compareInts(object.getManaCost().convertedManaCost(), convertedManaCost, convertedManaCostComparison))
				return false;
		}

		if (powerComparison != null) {
			if (!compareInts(object.getPower().getValue(), power, powerComparison))
				return false;
		}

		if (toughnessComparison != null) {
			if (!compareInts(object.getToughness().getValue(), toughness, toughnessComparison))
				return false;
		}

		return true;
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

	public void setScopeCardType(ComparisonScope scopeCardType) {
		this.scopeCardType = scopeCardType;
	}

	public void setNotCardType(boolean notCardType) {
		this.notCardType = notCardType;
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
	
}

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

import mage.Constants.CardType;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.keyword.ChangelingAbility;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author BetaSteward_at_googlemail.com
 * @author North
 */
public class FilterObject<E extends MageObject, T extends FilterObject<E, T>> extends FilterImpl<E, T> implements Filter<E> {
    protected Abilities<Ability> abilities;
    protected boolean notAbilities;
    protected List<CardType> cardType = new ArrayList<CardType>();
    protected ComparisonScope scopeCardType = ComparisonScope.Any;
    protected List<CardType> notCardType = new ArrayList<CardType>();
    protected ComparisonScope scopeNotCardType = ComparisonScope.Any;
    protected boolean colorless;
    protected boolean useColorless;
    protected boolean useColor;
    protected ObjectColor color;
    protected ComparisonScope scopeColor = ComparisonScope.Any;
    protected boolean notColor;
    protected List<String> subtype = new ArrayList<String>();
    protected ComparisonScope scopeSubtype = ComparisonScope.All;
    protected boolean notSubtype;
    protected List<String> supertype = new ArrayList<String>();
    protected ComparisonScope scopeSupertype = ComparisonScope.Any;
    protected boolean notSupertype;
    protected int power;
    protected ComparisonType powerComparison;
    protected int toughness;
    protected ComparisonType toughnessComparison;

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
        this.cardType.addAll(filter.cardType);
        this.notCardType.addAll(filter.notCardType);
        this.scopeCardType = filter.scopeCardType;
        this.scopeNotCardType = filter.scopeNotCardType;
        this.colorless = filter.colorless;
        this.useColorless = filter.useColorless;
        this.useColor = filter.useColor;
        this.color = filter.color.copy();
        this.scopeColor = filter.scopeColor;
        this.notColor = filter.notColor;
        this.subtype.addAll(filter.subtype);
        this.scopeSubtype = filter.scopeSubtype;
        this.notSubtype = filter.notSubtype;
        this.supertype.addAll(filter.supertype);
        this.scopeSupertype = filter.scopeSupertype;
        this.notSupertype = filter.notSupertype;
        this.power = filter.power;
        this.powerComparison = filter.powerComparison;
        this.toughness = filter.toughness;
        this.toughnessComparison = filter.toughnessComparison;
        this.another = filter.another;
    }

    @Override
    public boolean match(E object, Game game) {
        if (!super.match(object, game)) {
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
            if (!compCardType.compare(cardType, object.getCardType(), scopeCardType, false))
                return notFilter;
        }

        if (notCardType.size() > 0) {
            if (compCardType.compare(notCardType, object.getCardType(), scopeNotCardType, false))
                return notFilter;
        }

        if (subtype.size() > 0) {
            if (!object.getAbilities().contains(ChangelingAbility.getInstance())) {
                if (!compString.compare(subtype, object.getSubtype(), scopeSubtype, notSubtype))
                    return notFilter;
            }
        }

        if (supertype.size() > 0) {
            if (!compString.compare(supertype, object.getSupertype(), scopeSupertype, notSupertype))
                return notFilter;
        }

        if (abilities.size() > 0) {
            List<Ability> test = new ArrayList<Ability>(abilities);
            for (Ability ability: object.getAbilities()) {
                for (Ability abilityTest: test) {
                    if (ability == abilityTest || ability.getClass().equals(abilityTest.getClass())) {
                        test.remove(abilityTest);
                        break;
                    }
                }
            }
            if (test.isEmpty() == notAbilities)
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
        return this.notCardType;
    }

    public void setScopeCardType(ComparisonScope scopeCardType) {
        this.scopeCardType = scopeCardType;
    }

    public void setScopeNotCardType(ComparisonScope scopeNotCardType) {
        this.scopeNotCardType = scopeNotCardType;
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

    public boolean isAnother() {
        return another;
    }

    public void setAnother(boolean another) {
        this.another = another;
    }
}

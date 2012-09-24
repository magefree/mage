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

package mage;

import mage.Constants.CardType;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class MageObjectImpl<T extends MageObjectImpl<T>> implements MageObject {

    protected UUID objectId;

    protected String name;
    protected ManaCosts<ManaCost> manaCost;
    protected ObjectColor color;
    protected List<CardType> cardType = new ArrayList<CardType>();
    protected List<String> subtype = new ArrayList<String>();
    protected List<String> supertype = new ArrayList<String>();
    protected Abilities<Ability> abilities;
    protected String text;
    protected MageInt power;
    protected MageInt toughness;
    protected boolean copy;

    @Override
    public abstract T copy();

    public MageObjectImpl() {
        this(UUID.randomUUID());
    }

    public MageObjectImpl(UUID id) {
        objectId = id;
        power = new MageInt(0);
        toughness = new MageInt(0);
        color = new ObjectColor();
        manaCost = new ManaCostsImpl<ManaCost>("");
        abilities = new AbilitiesImpl<Ability>();
    }

    public MageObjectImpl(final MageObjectImpl<T> object) {
        objectId = object.objectId;
        name = object.name;
        manaCost = object.manaCost.copy();
        text = object.text;
        color = object.color.copy();
        power = object.power.copy();
        toughness = object.toughness.copy();
        abilities = object.abilities.copy();
        this.cardType.addAll(object.cardType);
        this.subtype.addAll(object.subtype);
        this.supertype.addAll(object.supertype);
    }

    @Override
    public UUID getId() {
         return objectId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<CardType> getCardType() {
        return cardType;
    }

    @Override
    public List<String> getSubtype(){
        return subtype;
    }

    @Override
    public List<String> getSupertype(){
        return supertype;
    }

    @Override
    public Abilities<Ability> getAbilities(){
        return abilities;
    }

    @Override
    public MageInt getPower() {
        return power;
    }

    @Override
    public MageInt getToughness() {
        return toughness;
    }

    @Override
    public ObjectColor getColor() {
        return color;
    }

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        return manaCost;
    }

    @Override
    public void adjustChoices(Ability ability, Game game) {}
    
    @Override
    public void adjustCosts(Ability ability, Game game) {}

    @Override
    public void adjustTargets(Ability ability, Game game) {}

    @Override
    public boolean hasSubtype(String value) {
        if (value == null) {
            return false;
        }
        if (this.subtype.contains(value)) {
            return true;
        }
        else { // checking for Changeling
            // first make sure input parameter is not creature type
            // if so, then ChangelingAbility doesn't matter
            if (value.equals("Mountain") || value.equals("Island") || value.equals("Plains")
                    || value.equals("Forest") || value.equals("Swamp") || value.equals("Aura")
                    || value.equals("Equipment") || value.equals("Fortification") || value.equals("Shrine")) {
                return false;
            }
            // as it is creature subtype, then check the existence of Changeling
            return abilities.contains(ChangelingAbility.getInstance());
        }
    }

    @Override
    public void setCopy(boolean isCopy) {
        this.copy = isCopy;
    }

    @Override
    public boolean isCopy() {
        return copy;
    }
}

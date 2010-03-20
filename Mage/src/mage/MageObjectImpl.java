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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mage.Constants.*;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.costs.mana.ManaCosts;

public abstract class MageObjectImpl implements MageObject {

	protected UUID objectId;
	
	protected String name;
	protected ManaCosts manaCost = new ManaCosts("");
	protected ObjectColor color = new ObjectColor();
	protected List<CardType> cardType = new ArrayList<CardType>();
	protected List<String> subtype = new ArrayList<String>();
	protected List<String> supertype = new ArrayList<String>();
	protected Abilities abilities = new AbilitiesImpl();
	protected String text;
	protected MageInt power = new MageInt(0);
	protected MageInt toughness = new MageInt(0);
	protected MageInt loyalty = new MageInt(0);
	protected Zone zone;

	public MageObjectImpl() {
		objectId = UUID.randomUUID();
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
	public Abilities getAbilities(){
		return abilities;
	}

	@Override
	public MageInt getPower() {
//		if (power != null)
			return power;
//		return MageInt.EmptyMageInt;
	}

	@Override
	public MageInt getToughness() {
//		if (toughness != null)
			return toughness;
//		return MageInt.EmptyMageInt;
	}

	@Override
	public MageInt getLoyalty() {
//		if (loyalty != null)
			return loyalty;
//		return MageInt.EmptyMageInt;
	}

	@Override
	public ObjectColor getColor() {
		return color;
	}

	@Override
	public ManaCosts getManaCost() {
		return manaCost;
	}

	@Override
	public Zone getZone() {
		return zone;
	}

	@Override
	public void setZone(Zone zone) {
		this.zone = zone;
	}
}

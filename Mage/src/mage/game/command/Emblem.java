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
package mage.game.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.game.Game;

/**
 * @author nantuko
 */
public class Emblem implements CommandObject {

	private static List emptyList = new ArrayList();
	private static ObjectColor emptyColor = new ObjectColor();
	private static ManaCosts emptyCost = new ManaCostsImpl();


	private UUID id;
	private UUID controllerId;
	private UUID sourceId;
	private Abilities<Ability> abilites = new AbilitiesImpl<Ability>();

	public Emblem() {
		this.id = UUID.randomUUID();
	}

	public Emblem(Emblem emblem) {
		this.id = emblem.id;
		this.controllerId = emblem.controllerId;
		this.sourceId = emblem.sourceId;
		this.abilites = emblem.abilites.copy();
	}

	@Override
	public UUID getSourceId() {
		return this.sourceId;
	}

	@Override
	public UUID getControllerId() {
		return this.controllerId;
	}

	public void setControllerId(UUID controllerId) {
	 	this.controllerId = controllerId;
        this.abilites.setControllerId(controllerId);
	}

	public void setSourceId(UUID sourceId) {
		this.sourceId = sourceId;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public void setName(String name) {}

	@Override
	public List<Constants.CardType> getCardType() {
		return emptyList;
	}

	@Override
	public List<String> getSubtype() {
		return emptyList;
	}

	@Override
	public boolean hasSubtype(String subtype) {
		return false;
	}

	@Override
	public List<String> getSupertype() {
		return emptyList;
	}

	@Override
	public Abilities<Ability> getAbilities() {
		return abilites;
	}

	@Override
	public ObjectColor getColor() {
		return emptyColor;
	}

	@Override
	public ManaCosts<ManaCost> getManaCost() {
		return emptyCost;
	}

	@Override
	public MageInt getPower() {
		return MageInt.EmptyMageInt;
	}

	@Override
	public MageInt getToughness() {
		return MageInt.EmptyMageInt;
	}

	@Override
	public void adjustCosts(Ability ability, Game game) {}

	@Override
	public UUID getId() {
		return this.id;
	}

    @Override
	public Emblem copy() {
		return new Emblem(this);
	}
}

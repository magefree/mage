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

package mage.game.stack;

import java.util.ArrayList;
import mage.Constants.AbilityType;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.costs.AlternativeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.choices.Choice;
import mage.choices.Choices;
import mage.game.*;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.EffectType;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.game.events.GameEvent;
import mage.target.Target;
import mage.target.Targets;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class StackAbility implements StackObject, Ability {

	private static List emptyList = new ArrayList();
	private static ObjectColor emptyColor = new ObjectColor();
	private static ManaCosts emptyCost = new ManaCostsImpl();
	private static Costs emptyCosts = new CostsImpl();
	private static Abilities<Ability> emptyAbilites = new AbilitiesImpl<Ability>();

	private Ability ability;
	private UUID controllerId;

	public StackAbility(Ability ability, UUID controllerId) {
		this.ability = ability;
		this.controllerId = controllerId;
	}

	public StackAbility(final StackAbility spell) {
		this.ability = spell.ability.copy();
		this.controllerId = spell.controllerId;
	}

	@Override
	public boolean resolve(Game game) {
		if (ability.getTargets().stillLegal(ability, game)) {
			return ability.resolve(game);
		}
		counter(null, game);
		return false;
	}

	@Override
	public void reset(Game game) { }
	
	@Override
	public void counter(UUID sourceId, Game game) {
		//20100716 - 603.8
		if (ability instanceof StateTriggeredAbility) {
			((StateTriggeredAbility)ability).counter(game);
		}
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public List<CardType> getCardType() {
		return emptyList;
	}

	@Override
	public List<String> getSubtype() {
		return emptyList;
	}

	@Override
	public List<String> getSupertype() {
		return emptyList;
	}

	@Override
	public Abilities getAbilities() {
		return emptyAbilites;
	}

	@Override
	public ObjectColor getColor() {
		return emptyColor;
	}

	@Override
	public ManaCosts getManaCost() {
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
	public Zone getZone() {
		return this.ability.getZone();
	}

	@Override
	public UUID getId() {
		return this.ability.getId();
	}

	@Override
	public UUID getSourceId() {
		return this.ability.getSourceId();
	}

	@Override
	public UUID getControllerId() {
		return this.controllerId;
	}

	@Override
	public Costs getCosts() {
		return emptyCosts;
	}

	@Override
	public Effects getEffects() {
		return ability.getEffects();
	}

	@Override
	public Effects getEffects(EffectType effectType) {
		return ability.getEffects(effectType);
	}

	@Override
	public String getRule() {
		return ability.getRule();
	}

	@Override
	public String getRule(boolean all) {
		return ability.getRule(all);
	}

	@Override
	public String getRule(String source) {
		return ability.getRule(source);
	}

	@Override
	public void setControllerId(UUID controllerId) {
		this.controllerId = controllerId;
	}

	@Override
	public void setSourceId(UUID sourceID) {}

	@Override
	public void addCost(Cost cost) {}

	@Override
	public void addEffect(Effect effect) {}

	@Override
	public boolean activate(Game game, boolean noMana) {
		return ability.activate(game, noMana);
	}

	@Override
	public Targets getTargets() {
		return ability.getTargets();
	}

	@Override
	public void addTarget(Target target) {}

	@Override
	public UUID getFirstTarget() {
		return ability.getFirstTarget();
	}

	@Override
	public Choices getChoices() {
		return ability.getChoices();
	}

	@Override
	public void addChoice(Choice choice) {}

	@Override
	public List<AlternativeCost> getAlternativeCosts() {
		return ability.getAlternativeCosts();
	}

	@Override
	public void addAlternativeCost(AlternativeCost cost) { }

	@Override
	public ManaCosts getManaCosts() {
		return ability.getManaCosts();
	}

	@Override
	public ManaCosts<ManaCost> getManaCostsToPay ( ) {
		return ability.getManaCostsToPay();
	}

	@Override
	public void addManaCost(ManaCost cost) { }

	@Override
	public void checkTriggers(GameEvent event, Game game) {	}

	@Override
	public AbilityType getAbilityType() {
		return ability.getAbilityType();
	}

	@Override
	public boolean isUsesStack() {
		return true;
	}

	@Override
	public StackAbility copy() {
		return new StackAbility(this);
	}

	@Override
	public void setName(String name) { }

	@Override
	public void adjustCosts(Ability ability, Game game) {}

	@Override
	public Costs<Cost> getOptionalCosts() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void addOptionalCost(Cost cost) {}

	@Override
	public boolean checkIfClause(Game game) {
		return true;
	}

	@Override
	public void newId() {}

	public Ability getStackAbility() {
		return ability;
	}

	@Override
	public boolean isModal() {
		return ability.isModal();
	}

	@Override
	public void addMode(Mode mode) {}

	@Override
	public Modes getModes() {
		return ability.getModes();
	}
}

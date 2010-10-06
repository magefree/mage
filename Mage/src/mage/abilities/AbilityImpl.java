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

package mage.abilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import mage.Constants.AbilityType;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.costs.AlternativeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.Choices;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;
import mage.util.Logging;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class AbilityImpl<T extends AbilityImpl<T>> implements Ability {

	private final static transient Logger logger = Logging.getLogger(AbilityImpl.class.getName());

	protected final UUID id;
	protected AbilityType abilityType;
	protected UUID controllerId;
	protected UUID sourceId;
	protected ManaCosts<ManaCost> manaCosts;
	protected Costs<Cost> costs;
	protected ArrayList<AlternativeCost> alternativeCosts = new ArrayList<AlternativeCost>();
	protected Costs<Cost> optionalCosts;
	protected Targets targets;
	protected Choices choices;
	protected Effects effects;
	protected Zone zone;
	protected String name;
	protected boolean usesStack = true;

	@Override
	public abstract T copy();

	public AbilityImpl(AbilityType abilityType, Zone zone) {
		this.id = UUID.randomUUID();
		this.abilityType = abilityType;
		this.zone = zone;
		this.manaCosts = new ManaCostsImpl<ManaCost>();
		this.costs = new CostsImpl<Cost>();
		this.optionalCosts = new CostsImpl<Cost>();
		this.effects = new Effects();
		this.targets = new Targets();
		this.choices = new Choices();
	}

	public AbilityImpl(AbilityImpl<T> ability) {
		this.id = ability.id;
		this.abilityType = ability.abilityType;
		this.controllerId = ability.controllerId;
		this.sourceId = ability.sourceId;
		this.zone = ability.zone;
		this.name = ability.name;
		this.usesStack = ability.usesStack;
		this.manaCosts = ability.manaCosts.copy();
		this.costs = ability.costs.copy();
		this.optionalCosts = ability.optionalCosts.copy();
		for (AlternativeCost cost: ability.alternativeCosts) {
			this.alternativeCosts.add((AlternativeCost)cost.copy());
		}
		this.targets = ability.targets.copy();
		this.choices = ability.choices.copy();
		this.effects = ability.effects.copy();
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public AbilityType getAbilityType() {
		return this.abilityType;
	}

	@Override
	public boolean resolve(Game game) {
		boolean result = true;
		//20100716 - 117.12
		if (checkIfClause(game)) {
			for (Effect effect: getEffects()) {
				if (effect instanceof OneShotEffect) {
					result &= effect.apply(game, this);
				}
				else {
					game.addEffect((ContinuousEffect) effect, this);
				}
			}
		}
		return result;
	}

	@Override
	public boolean activate(Game game, boolean noMana) {
		if (choices.size() > 0 && choices.choose(game, this) == false) {
			logger.fine("activate failed - choice");
			return false;
		}
		if (targets.size() > 0 && targets.chooseTargets(effects.get(0).getOutcome(), this.controllerId, this, game) == false) {
			logger.fine("activate failed - target");
			return false;
		}
		if (!useAlternativeCost(game)) {
			if (!manaCosts.pay(game, sourceId, controllerId, noMana)) {
				logger.fine("activate failed - mana");
				return false;
			}
		}
		game.getObject(sourceId).adjustCosts(this, game);
		if (!costs.pay(game, sourceId, controllerId, noMana)) {
			logger.fine("activate failed - non mana costs");
			return false;
		}
		return true;
	}

	@Override
	public void reset(Game game) {}

	protected boolean useAlternativeCost(Game game) {
		for (AlternativeCost cost: alternativeCosts) {
			if (cost.isAvailable(game, this)) {
				if (game.getPlayer(this.controllerId).chooseUse(Outcome.Neutral, "Use alternative cost " + cost.getName(), game))
					return cost.pay(game, sourceId, controllerId, false);
			}
		}
		return false;
	}

	@Override
	public boolean checkIfClause(Game game) {
		return true;
	}

	@Override
	public UUID getControllerId() {
		return controllerId;
	}

	@Override
	public void setControllerId(UUID controllerId) {
		this.controllerId = controllerId;
	}


	@Override
	public UUID getSourceId() {
		return sourceId;
	}

	@Override
	public void setSourceId(UUID sourceId) {
		this.sourceId = sourceId;
	}

	@Override
	public Costs getCosts() {
		return costs;
	}

	@Override
	public ManaCosts<ManaCost> getManaCosts() {
		return manaCosts;
	}

	@Override
	public List<AlternativeCost> getAlternativeCosts() {
		return alternativeCosts;
	}

	@Override
	public Costs getOptionalCosts() {
		return optionalCosts;
	}

	@Override
	public Effects getEffects() {
		return effects;
	}

	@Override
	public Choices getChoices() {
		return choices;
	}

	@Override
	public Zone getZone() {
		return zone;
	}

	@Override
	public boolean isUsesStack() {
		return usesStack;
	}

	@Override
	public String getRule() {
		StringBuilder sbRule = new StringBuilder();

		if (!(this.abilityType == AbilityType.SPELL)) {
			if (manaCosts.size() > 0) {
				sbRule.append(manaCosts.getText());
			}
			if (costs.size() > 0) {
				if (sbRule.length() > 0) {
					sbRule.append(",");
				}
				sbRule.append(costs.getText());
			}
			if (sbRule.length() > 0) {
				sbRule.append(": ");
			}
		}
		else if (this.alternativeCosts.size() > 0) {
			for (AlternativeCost cost: alternativeCosts) {
				sbRule.append(cost.getText()).append("\n");
			}
		}
		else {
			for (Cost cost: this.costs) {
				if (!(cost instanceof ManaCost)) {
					sbRule.append("As an additional cost to cast {this}, ").append(cost.getText()).append("\n");
				}
			}
		}

		sbRule.append(effects.getText(this));

		return sbRule.toString();
	}

	@Override
	public void addCost(Cost cost) {
		if (cost != null) {
			this.costs.add(cost);
		}
	}

	@Override
	public void addManaCost(ManaCost cost) {
		if (cost != null) {
			this.manaCosts.add(cost);
		}
	}

	@Override
	public void addAlternativeCost(AlternativeCost cost) {
		if (cost != null) {
			this.alternativeCosts.add(cost);
		}
	}

	@Override
	public void addOptionalCost(Cost cost) {
		if (cost != null) {
			this.optionalCosts.add(cost);
		}
	}

	@Override
	public void addEffect(Effect effect) {
		if (effect != null) {
			this.effects.add(effect);
		}
	}

	@Override
	public void addTarget(Target target) {
		if (target != null) {
			this.targets.add(target);
		}
	}

	@Override
	public void addChoice(Choice choice) {
		if (choice != null) {
			this.choices.add(choice);
		}
	}

	@Override
	public Targets getTargets() {
		return this.targets;
	}

	@Override
	public UUID getFirstTarget() {
		return targets.getFirstTarget();
	}

	@Override
	public String toString() {
		return getRule();
	}
}

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.abilities.costs.AlternativeCost;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.OneShotEffect;
import mage.choices.Choice;
import mage.choices.Choices;
import mage.game.Game;
import mage.target.Target;
import mage.target.Targets;
import mage.util.Copier;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class AbilityImpl implements Ability, Serializable {

	protected static Copier<Ability> copier = new Copier<Ability>();

	protected UUID id;
	protected UUID controllerId;
	protected UUID sourceId;
	protected ManaCosts manaCosts = new ManaCosts(this);
	protected Costs<Cost> costs = new CostsImpl<Cost>(this);
	protected List<AlternativeCost> alternativeCosts = new ArrayList<AlternativeCost>();
	protected Targets targets = new Targets(this);
	protected Choices choices = new Choices(this);
	protected Effects effects = new Effects(this);
	protected Zone zone;
	protected String name;
	protected boolean enabled = true;

	public AbilityImpl(Zone zone) {
		this.id = UUID.randomUUID();
		this.zone = zone;
		targets.setSource(this);
		costs.setAbility(this);
		choices.setSource(this);
		effects.setSource(this);
	}

	@Override
	public UUID getId() {
		return id;
	}

	@Override
	public boolean resolve(Game game) {
		boolean result = true;
		if (enabled) {
			for (Effect effect: getEffects()) {
				if (effect instanceof OneShotEffect) {
					result &= effect.apply(game);
				}
				else {
					game.addEffect((ContinuousEffect) effect);
				}
			}
		}
		return result;
	}

	@Override
	public boolean activate(Game game, boolean noMana) {
		if (choices.size() > 0 && choices.choose(game) == false)
			return false;
		if (targets.size() > 0 && targets.choose(effects.get(0).getOutcome(), game) == false)
			return false;
		if (!useAlternativeCost(game)) {
			if (!manaCosts.pay(game, noMana))
				return false;
		}
		return costs.pay(game, noMana);
	}

	protected boolean useAlternativeCost(Game game) {
		for (AlternativeCost cost: alternativeCosts) {
			if (cost.isAvailable(game)) {
				if (game.getPlayer(this.controllerId).chooseUse(Outcome.Neutral, "Use alternative cost " + cost.getName(), game))
					return cost.pay(game, false);
			}
		}
		return false;
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
	public ManaCosts getManaCosts() {
		return manaCosts;
	}

	@Override
	public List<AlternativeCost> getAlternativeCosts() {
		return alternativeCosts;
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
	public String getRule() {
		StringBuilder sbRule = new StringBuilder();

		if (!(this instanceof SpellAbility)) {
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

		sbRule.append(effects.getText());

		return sbRule.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void addCost(Cost cost) {
		if (cost != null) {
			cost.setAbility(this);
			this.costs.add(cost);
		}
	}

	@Override
	public void addManaCost(ManaCost cost) {
		if (cost != null) {
			cost.setAbility(this);
			this.manaCosts.add(cost);
		}
	}

	@Override
	public void addAlternativeCost(AlternativeCost cost) {
		if (cost != null) {
			cost.setAbility(this);
			this.alternativeCosts.add(cost);
		}
	}

	@Override
	public void addEffect(Effect effect) {
		if (effect != null) {
			effect.setSource(this);
			this.effects.add(effect);
		}
	}

	@Override
	public void addTarget(Target target) {
		if (target != null) {
			target.setAbility(this);
			this.targets.add(target);
		}
	}

	@Override
	public void addChoice(Choice choice) {
		if (choice != null) {
			choice.setAbility(this);
			this.choices.add(choice);
		}
	}

	@Override
	public Targets getTargets() {
		return this.targets;
	}

	@Override
	public UUID getFirstTarget() {
		if (this.targets.size() > 0)
			return this.targets.get(0).getFirstTarget();
		return null;
	}

	@Override
	public Ability copy() {
		return copier.copy(this);
	}
}

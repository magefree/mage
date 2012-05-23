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

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.mana.conditional.ManaCondition;
import mage.filter.Filter;
import mage.filter.FilterMana;
import mage.game.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public class ConditionalMana extends Mana implements Serializable {

	/**
	 * Conditions that should be met (all or any depending on comparison scope) to allow spending {@link Mana} mana.
	 */
	private List<Condition> conditions = new ArrayList<Condition>();

	/**
	 * Text displayed as a description for conditional mana.
	 * Usually includes the conditions that should be met to use this mana.
	 */
	protected String staticText = "Conditional mana.";

	/**
	 * By default all conditions should be met
	 */
	private Filter.ComparisonScope scope = Filter.ComparisonScope.All;

    /**
     * UUID of source for mana.
     */
    private UUID manaProducerId;

	public ConditionalMana(Mana mana) {
		super(mana);
	}

	public ConditionalMana(ConditionalMana conditionalMana) {
		super(conditionalMana);
		conditions = conditionalMana.conditions;
		scope = conditionalMana.scope;
		staticText = conditionalMana.staticText;
	}

	public void addCondition(Condition condition) {
		this.conditions.add(condition);
	}

	public void setComparisonScope(Filter.ComparisonScope scope) {
		this.scope = scope;
	}

	public boolean apply(Ability ability, Game game, UUID manaProducerId) {
		if (conditions.size() == 0) {
			throw new IllegalStateException("Conditional mana should contain at least one Condition");
		}
		for (Condition condition : conditions) {
            boolean applied = (condition instanceof ManaCondition) ?
                    ((ManaCondition)condition).apply(game, ability, manaProducerId) : condition.apply(game, ability);

            if (!applied) {
				// if one condition fails, return false only if All conditions should be met
				// otherwise it may happen that Any other condition will be ok
				if (scope.equals(Filter.ComparisonScope.All)) {
					return false;
				}
			} else {
				// if one condition succeeded, return true only if Any conditions should be met
				// otherwise it may happen that any other condition will fail
				if (scope.equals(Filter.ComparisonScope.Any)) {
					return true;
				}
			}
		}
		// we are here
		// if All conditions should be met, then it's Ok (return true)
		// if Any, then it should have already returned true, so returning false here
		return scope.equals(Filter.ComparisonScope.All);
	}

	@Override
	public ConditionalMana copy() {
		return new ConditionalMana(this);
	}

	public String getDescription() {
		return staticText;
	}

    public void removeAll(FilterMana filter) {
		if (filter == null) {
			return;
		}
		if (filter.isBlack()) black = 0;
		if (filter.isBlue()) blue = 0;
		if (filter.isWhite()) white = 0;
		if (filter.isGreen()) green = 0;
		if (filter.isRed()) red = 0;
		if (filter.isColorless()) colorless = 0;
    }

    public UUID getManaProducerId() {
        return manaProducerId;
    }

    public void setManaProducerId(UUID manaProducerId) {
        this.manaProducerId = manaProducerId;
    }
}

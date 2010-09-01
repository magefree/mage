/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 * 
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 * 
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 * 
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.abilities.keyword.LevelAbility;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public abstract class LevelerCard<T extends LevelerCard<T>> extends CardImpl<T> {

	protected List<LevelAbility> levels = new ArrayList<LevelAbility>();

	public LevelerCard(UUID ownerId, String name, CardType[] cardTypes, String costs) {
		super(ownerId, name, cardTypes, costs);
	}

	public LevelerCard(LevelerCard card) {
		super(card);
		for (LevelAbility ability: (List<LevelAbility>)card.levels) {
			this.levels.add(ability.copy());
		}
	}

	public List<LevelAbility> getLevels() {
		return levels;
	}

	public LevelAbility getLevel(int level) {
		for (LevelAbility levelerLevel: levels) {
			if (level >= levelerLevel.getLevel1() && (levelerLevel.getLevel2() == -1 || level <= levelerLevel.getLevel2()))
				return levelerLevel;
		}
		return null;
	}

	@Override
	public List<String> getRules() {
		List<String> rules = new ArrayList<String>();
		rules.addAll(super.getRules());
		for (LevelAbility ability: levels) {
			rules.add(ability.getRule());
		}
		return rules;
	}
}

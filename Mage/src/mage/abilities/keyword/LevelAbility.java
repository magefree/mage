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

package mage.abilities.keyword;

import mage.Constants.Zone;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class LevelAbility extends StaticAbility<LevelAbility> {

	private int level1;
	private int level2;
	private Abilities<Ability> abilities = new AbilitiesImpl<Ability>();
	private int power;
	private int toughness;

	public LevelAbility(int level1, int level2, Abilities<Ability> abilities, int power, int toughness) {
		super(Zone.BATTLEFIELD, null);
		this.level1 = level1;
		this.level2 = level2;
		this.abilities.addAll(abilities);
		this.power = power;
		this.toughness = toughness;
	}

	public LevelAbility(LevelAbility ability) {
		super(ability);
		this.level1 = ability.level1;
		this.level2 = ability.level2;
		this.abilities = ability.abilities.copy();
		this.power = ability.power;
		this.toughness = ability.toughness;
	}

	public int getLevel1() {
		return level1;
	}

	public int getLevel2() {
		return level2;
	}

	public Abilities<Ability> getAbilities() {
		return abilities;
	}

	public int getPower() {
		return power;
	}

	public int getToughness() {
		return toughness;
	}

	@Override
	public String getRule() {
		StringBuilder sb = new StringBuilder();
		sb.append("Level ").append(level1);
		if (level2 == -1)
			sb.append("+");
		else
			sb.append("-").append(level2);
		sb.append(": ").append(power).append("/").append(toughness).append(" ");
		for (String rule: abilities.getRules()) {
			sb.append(rule).append(" ");
		}
		return sb.toString();
	}

	@Override
	public LevelAbility copy() {
		return new LevelAbility(this);
	}

}

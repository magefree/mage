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

package mage.abilities.mana;

import java.util.ArrayList;
import java.util.List;
import mage.Mana;
import mage.game.Game;
import mage.util.Copier;

/**
 *
 * @author BetaSteward_at_googlemail.com
 *
 * this class is used to build a list of all possible mana combinations
 * it can be used to find all the ways to pay a mana cost
 * or all the different mana combinations available to a player
 *
 */
public class ManaOptions extends ArrayList<Mana> {

	public void addMana(List<ManaAbility> abilities, Game game) {
		if (isEmpty())
			this.add(new Mana());
		if (!abilities.isEmpty()) {
			if (abilities.size() == 1) {
				//if there is only one mana option available add it to all the existing options
				addMana(abilities.get(0).getNetMana(game));
			}
			else if (abilities.size() > 1) {
				//perform a union of all existing options and the new options
				Copier<List<Mana>> copier = new Copier<List<Mana>>();
				List<Mana> copy = copier.copy(this);
				this.clear();
				for (ManaAbility ability: abilities) {
					for (Mana mana: copy) {
						Mana newMana = new Mana();
						newMana.add(mana);
						newMana.add(ability.getNetMana(game));
						this.add(newMana);
					}
				}
			}
		}
	}

	public void addMana(Mana addMana) {
		if (isEmpty())
			this.add(new Mana());
		for (Mana mana: this) {
			mana.add(addMana);
		}
	}

	public void addMana(ManaOptions options) {
		if (isEmpty())
			this.add(new Mana());
		if (!options.isEmpty()) {
			if (options.size() == 1) {
				//if there is only one mana option available add it to all the existing options
				addMana(options.get(0));
			}
			else if (options.size() > 1) {
				//perform a union of all existing options and the new options
				Copier<List<Mana>> copier = new Copier<List<Mana>>();
				List<Mana> copy = copier.copy(this);
				this.clear();
				for (Mana addMana: options) {
					for (Mana mana: copy) {
						Mana newMana = new Mana();
						newMana.add(mana);
						newMana.add(addMana);
						this.add(newMana);
					}
				}
			}
		}
	}

}
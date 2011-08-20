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

package mage.players;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.filter.Filter;
import mage.filter.FilterMana;
import mage.game.Game;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ManaPool implements Serializable {

	private int red = 0;
	private int green = 0;
	private int blue = 0;
	private int white = 0;
	private int black = 0;
	private int colorless = 0;

	private List<ConditionalMana> conditionalMana = new ArrayList<ConditionalMana>();

	public ManaPool() {}

	public ManaPool(final ManaPool pool) {
		this.red = pool.red;
		this.green = pool.green;
		this.blue = pool.blue;
		this.white = pool.white;
		this.black = pool.black;
		this.colorless = pool.colorless;
		for (ConditionalMana mana : pool.conditionalMana) {
			conditionalMana.add(mana.copy());
		}
	}

	public void setRed(int red) {
		this.red = red;
	}

	public void removeRed() {
		red--;
	}

	public void addRed() {
		red++;
	}

	public int getRed() {
		return red;
	}

	public int getConditionalRed(Ability ability, Game game) {
		if (ability == null || conditionalMana.size() == 0) {
			return 0;
		}
		boolean hasRed = false;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getRed() > 0) {
				hasRed = true;
				break;
			}
		}
		if (!hasRed) return 0;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getRed() > 0 && mana.apply(ability, game)) {
				return mana.getRed();
			}
		}
		return 0;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public void removeGreen() {
		green--;
	}

	public void addGreen() {
		green++;
	}

	public int getGreen() {
		return green;
	}

	public int getConditionalGreen(Ability ability, Game game) {
		if (ability == null || conditionalMana.size() == 0) {
			return 0;
		}
		boolean hasGreen = false;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getGreen() > 0) {
				hasGreen = true;
				break;
			}
		}
		if (!hasGreen) return 0;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getGreen() > 0 && mana.apply(ability, game)) {
				return mana.getGreen();
			}
		}
		return 0;
	}

	public void setBlue(int blue) {
		this.blue = blue;
}

	public void removeBlue() {
		blue--;
	}

	public void addBlue() {
		blue++;
	}

	public int getBlue() {
		return blue;
	}

	public int getConditionalBlue(Ability ability, Game game) {
		if (ability == null || conditionalMana.size() == 0) {
			return 0;
		}
		boolean hasBlue = false;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getBlue() > 0) {
				hasBlue = true;
				break;
			}
		}
		if (!hasBlue) return 0;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getBlue() > 0 && mana.apply(ability, game)) {
				return mana.getBlue();
			}
		}
		return 0;
	}

	public void setWhite(int white) {
		this.white = white;
	}

	public void removeWhite() {
		white--;
	}

	public void addWhite() {
		white++;
	}

	public int getWhite() {
		return white;
	}

	public int getConditionalWhite(Ability ability, Game game) {
		if (ability == null || conditionalMana.size() == 0) {
			return 0;
		}
		boolean hasWhite = false;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getWhite() > 0) {
				hasWhite = true;
				break;
			}
		}
		if (!hasWhite) return 0;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getWhite() > 0 && mana.apply(ability, game)) {
				return mana.getWhite();
			}
		}
		return 0;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public void removeBlack() {
		black--;
	}

	public void addBlack() {
		black++;
	}

	public int getBlack() {
		return black;
	}

	public int getConditionalBlack(Ability ability, Game game) {
		if (ability == null || conditionalMana.size() == 0) {
			return 0;
		}
		boolean hasBlack = false;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getBlack() > 0) {
				hasBlack = true;
				break;
			}
		}
		if (!hasBlack) return 0;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getBlack() > 0 && mana.apply(ability, game)) {
				return mana.getBlack();
			}
		}
		return 0;
	}

	public int getConditionalColorless(Ability ability, Game game) {
		if (ability == null || conditionalMana.size() == 0) {
			return 0;
		}
		boolean hasColorless = false;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getColorless() > 0) {
				hasColorless = true;
				break;
			}
		}
		if (!hasColorless) return 0;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getColorless() > 0 && mana.apply(ability, game)) {
				return mana.getColorless();
			}
		}
		return 0;
	}


	public int getConditionalCount(Ability ability, Game game, FilterMana filter) {
		if (ability == null || conditionalMana.size() == 0) {
			return 0;
		}
		int count = 0;
		for (ConditionalMana mana : conditionalMana) {
			if (mana.apply(ability, game)) {
				count += mana.count(filter);
			}
		}
		return count;
	}

	public void setColorless(int colorless) {
		this.colorless = colorless;
	}

	public void removeColorless() {
		colorless--;
	}

	public void addColorless() {
		colorless++;
	}

	public int getColorless() {
		return colorless;
	}

	public int emptyPool() {
		int total = count();
		black = 0;
		blue = 0;
		white = 0;
		red = 0;
		green = 0;
		colorless = 0;
		conditionalMana.clear();
		return total;
	}

	public int emptyPoolConditional(Ability ability, Game game) {
		int total = count();
		black = 0;
		blue = 0;
		white = 0;
		red = 0;
		green = 0;
		colorless = 0;
		// remove only those mana that can be spent for ability
		Iterator<ConditionalMana> it = conditionalMana.iterator();
		while (it.hasNext()) {
			ConditionalMana mana = it.next();
			if (mana.apply(ability, game)) {
				total += mana.count();
				it.remove();
			}
		}
		return total;
	}

	public int emptyPoolConditional(Ability ability, Game game, FilterMana filter) {
		if (filter == null) {
			return emptyPoolConditional(ability, game);
		}
		int total = count(filter);
		if (filter.isBlack()) black = 0;
		if (filter.isBlue()) blue = 0;
		if (filter.isWhite()) white = 0;
		if (filter.isRed()) red = 0;
		if (filter.isGreen()) green = 0;
		if (filter.isColorless()) colorless = 0;
		// remove only those mana that can be spent for ability
		Iterator<ConditionalMana> it = conditionalMana.iterator();
		while (it.hasNext()) {
			ConditionalMana mana = it.next();
			if (mana.apply(ability, game)) {
				if (mana.count(filter) > 0) {
					total += mana.count();
					it.remove();
				}
			}
		}
		return total;
	}

	public Mana getMana() {
		Mana mana = new Mana();
		mana.setBlack(black);
		mana.setBlue(blue);
		mana.setColorless(colorless);
		mana.setGreen(green);
		mana.setRed(red);
		mana.setWhite(white);
		return mana;
	}

	public Mana getMana(FilterMana filter) {
		if (filter == null) {
			return getMana();
		}
		Mana mana = new Mana();
		if (filter.isBlack()) mana.setBlack(black);
		if (filter.isBlue()) mana.setBlue(blue);
		if (filter.isColorless()) mana.setColorless(colorless);
		if (filter.isGreen()) mana.setGreen(green);
		if (filter.isRed()) mana.setRed(red);
		if (filter.isWhite()) mana.setWhite(white);
		return mana;
	}

	public Mana getAllConditionalMana(Ability ability, Game game) {
		Mana mana = new Mana();
		mana.setColorless(getConditionalCount(ability, game, null));
		return mana;
	}

	public Mana getAllConditionalMana(Ability ability, Game game, FilterMana filter) {
		Mana mana = new Mana();
		mana.setColorless(getConditionalCount(ability, game, filter));
		return mana;
	}

	public void changeMana(Mana mana) {
		if (mana instanceof ConditionalMana) {
			this.conditionalMana.add((ConditionalMana)mana);
		} else {
			this.black += mana.getBlack();
			this.blue += mana.getBlue();
			this.white += mana.getWhite();
			this.red += mana.getRed();
			this.green += mana.getGreen();
			this.colorless += mana.getColorless();
		}
	}

	public List<ConditionalMana> getConditionalMana() {
		return conditionalMana;
	}

	public boolean checkMana(Mana mana) {
		if (this.black < mana.getBlack())
			return false;
		if (this.blue < mana.getBlue())
			return false;
		if (this.white < mana.getWhite())
			return false;
		if (this.red < mana.getRed())
			return false;
		if (this.green < mana.getGreen())
			return false;
		if (this.colorless < mana.getColorless())
			return false;

		return true;

	}

	public int count() {
		return red + green + blue + white + black + colorless;
	}

	public int count(FilterMana filter) {
		if (filter == null) {
			return count();
		}
		int count = 0;
		if (filter.isBlack()) count += black;
		if (filter.isBlue()) count += blue;
		if (filter.isWhite()) count += white;
		if (filter.isGreen()) count += green;
		if (filter.isRed()) count += red;
		if (filter.isColorless()) count += colorless;
		return count;
	}

	public ManaPool copy() {
		return new ManaPool(this);
	}

	public void removeConditionalBlack(Ability ability, Game game) {
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getBlack() > 0 && mana.apply(ability, game)) {
				mana.setBlack(mana.getBlack() - 1);
				break;
			}
		}
	}

	public void removeConditionalBlue(Ability ability, Game game) {
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getBlue() > 0 && mana.apply(ability, game)) {
				mana.setBlue(mana.getBlue() - 1);
				break;
			}
		}
	}

	public void removeConditionalWhite(Ability ability, Game game) {
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getWhite() > 0 && mana.apply(ability, game)) {
				mana.setWhite(mana.getWhite() - 1);
				break;
			}
		}
	}

	public void removeConditionalGreen(Ability ability, Game game) {
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getGreen() > 0 && mana.apply(ability, game)) {
				mana.setGreen(mana.getGreen() - 1);
				break;
			}
		}
	}

	public void removeConditionalRed(Ability ability, Game game) {
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getRed() > 0 && mana.apply(ability, game)) {
				mana.setRed(mana.getRed() - 1);
				break;
			}
		}
	}

	public void removeConditionalColorless(Ability ability, Game game) {
		for (ConditionalMana mana : conditionalMana) {
			if (mana.getColorless() > 0 && mana.apply(ability, game)) {
				mana.setColorless(mana.getColorless() - 1);
				break;
			}
		}
	}

}

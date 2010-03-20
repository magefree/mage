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
import mage.Mana;

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

	public void changeMana(Mana mana) {
		this.black += mana.getBlack();
		this.blue += mana.getBlue();
		this.white += mana.getWhite();
		this.red += mana.getRed();
		this.green += mana.getGreen();
		this.colorless += mana.getColorless();
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

}

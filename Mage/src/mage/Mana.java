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

import java.io.Serializable;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class Mana implements Comparable<Mana>, Serializable {

	private int red = 0;
	private int green = 0;
	private int blue = 0;
	private int white = 0;
	private int black = 0;
	private int colorless = 0;
	private int any = 0;

	public static Mana RedMana = RedMana(1);
	public static Mana GreenMana = GreenMana(1);
	public static Mana BlueMana = BlueMana(1);
	public static Mana WhiteMana = WhiteMana(1);
	public static Mana BlackMana = BlackMana(1);
	public static Mana ColorlessMana = ColorlessMana(1);

	public Mana() {}

	public static Mana RedMana(int num) {
		return new Mana(num, 0, 0, 0, 0, 0, 0);
	}

	public static Mana GreenMana(int num) {
		return new Mana(0, num, 0, 0, 0, 0, 0);
	}

	public static Mana BlueMana(int num) {
		return new Mana(0, 0, num, 0, 0, 0, 0);
	}

	public static Mana WhiteMana(int num) {
		return new Mana(0, 0, 0, num, 0, 0, 0);
	}

	public static Mana BlackMana(int num) {
		return new Mana(0, 0, 0, 0, num, 0, 0);
	}

	public static Mana ColorlessMana(int num) {
		return new Mana(0, 0, 0, 0, 0, num, 0);
	}

	public Mana(int red, int green, int blue, int white, int black, int colorless, int any) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.white = white;
		this.black = black;
		this.colorless = colorless;
		this.any = any;
	}

	public void add(Mana mana) {
		red += mana.getRed();
		green += mana.getGreen();
		blue += mana.getBlue();
		white += mana.getWhite();
		black += mana.getBlack();
		colorless += mana.getColorless();
		any += mana.getAny();
	}

	public void addRed() {
		red++;
	}

	public void addGreen() {
		green++;
	}

	public void addBlue() {
		blue++;
	}

	public void addWhite() {
		white++;
	}

	public void addBlack() {
		black++;
	}

	public void addColorless() {
		colorless++;
	}

	public void subtract(Mana mana) {
		red -= mana.getRed();
		green -= mana.getGreen();
		blue -= mana.getBlue();
		white -= mana.getWhite();
		black -= mana.getBlack();
		colorless -= mana.getColorless();
		any -= mana.getAny();
	}

	public int count() {
		return red +green + blue + white + black + colorless + any;
	}

	public void clear() {
		red = 0;
		green = 0;
		blue = 0;
		white = 0;
		black = 0;
		colorless = 0;
		any = 0;
	}

	@Override
	public String toString() {
		StringBuilder sbMana = new StringBuilder();

		for (int i = 0; i < red; i++)
			sbMana.append("{R}");
		for (int i = 0; i < green; i++)
			sbMana.append("{G}");
		for (int i = 0; i < blue; i++)
			sbMana.append("{U}");
		for (int i = 0; i < black; i++)
			sbMana.append("{B}");
		for (int i = 0; i < white; i++)
			sbMana.append("{W}");
		if (colorless > 0) {
			sbMana.append("{");
			sbMana.append(Integer.toString(colorless));
			sbMana.append("}");
		}
		return sbMana.toString();
	}

	public Mana copy() {
		Mana copy = new Mana();
		copy.add(this);
		return copy;
	}

	public boolean enough(Mana avail) {
		Mana compare = avail.copy();
		compare.subtract(this);
		if (compare.getRed() < 0) {
			compare.setAny(compare.getAny() + compare.getRed());
			if (compare.getAny() < 0)
				return false;
			compare.setRed(0);
		}
		if (compare.getGreen() < 0) {
			compare.setAny(compare.getAny() + compare.getGreen());
			if (compare.getAny() < 0)
				return false;
			compare.setGreen(0);
		}
		if (compare.getBlue() < 0) {
			compare.setAny(compare.getAny() + compare.getBlue());
			if (compare.getAny() < 0)
				return false;
			compare.setBlue(0);
		}
		if (compare.getBlack() < 0) {
			compare.setAny(compare.getAny() + compare.getBlack());
			if (compare.getAny() < 0)
				return false;
			compare.setBlack(0);
		}
		if (compare.getWhite() < 0) {
			compare.setAny(compare.getAny() + compare.getWhite());
			if (compare.getAny() < 0)
				return false;
			compare.setWhite(0);
		}
		if (compare.getColorless() < 0) {
			int remaining = compare.getRed() + compare.getGreen() + compare.getBlack() + compare.getBlue() + compare.getWhite();
			if (compare.getColorless() + remaining < 0)
				return false;
		}
		return true;
	}

	public Mana needed(Mana avail) {
		Mana compare = avail.copy();
		compare.subtract(this);
		if (compare.getRed() < 0 && compare.getAny() > 0) {
			int diff = Math.min( compare.getAny(), Math.abs(compare.getRed()));
			compare.setAny(compare.getAny() - diff);
			compare.setRed(compare.getRed() + diff);
		}
		if (compare.getGreen() < 0 && compare.getAny() > 0) {
			int diff = Math.min( compare.getAny(), Math.abs(compare.getGreen()));
			compare.setAny(compare.getAny() - diff);
			compare.setGreen(compare.getGreen() + diff);
		}
		if (compare.getBlue() < 0 && compare.getAny() > 0) {
			int diff = Math.min( compare.getAny(), Math.abs(compare.getBlue()));
			compare.setAny(compare.getAny() - diff);
			compare.setBlue(compare.getBlue() + diff);
		}
		if (compare.getBlack() < 0 && compare.getAny() > 0) {
			int diff = Math.min( compare.getAny(), Math.abs(compare.getBlack()));
			compare.setAny(compare.getAny() - diff);
			compare.setBlack(compare.getBlack() + diff);
		}
		if (compare.getWhite() < 0 && compare.getAny() > 0) {
			int diff = Math.min( compare.getAny(), Math.abs(compare.getWhite()));
			compare.setAny(compare.getAny() - diff);
			compare.setWhite(compare.getWhite() + diff);
		}
		if (compare.getColorless() < 0) {
			int remaining = 0;
			remaining += Math.min(0,compare.getRed());
			remaining += Math.min(0,compare.getWhite());
			remaining += Math.min(0,compare.getGreen());
			remaining += Math.min(0,compare.getBlack());
			remaining += Math.min(0,compare.getBlue());
			remaining += Math.min(0,compare.getAny());
			if (remaining > 0) {
				int diff = Math.min(remaining, Math.abs(compare.getColorless()));
				compare.setColorless(compare.getColorless() + diff);
			}
		}
		Mana needed = new Mana();
		if (compare.getRed() < 0)
			needed.setRed(Math.abs(compare.getRed()));
		if (compare.getWhite() < 0)
			needed.setWhite(Math.abs(compare.getWhite()));
		if (compare.getGreen() < 0)
			needed.setGreen(Math.abs(compare.getGreen()));
		if (compare.getBlack() < 0)
			needed.setBlack(Math.abs(compare.getBlack()));
		if (compare.getBlue() < 0)
			needed.setBlue(Math.abs(compare.getBlue()));
		if (compare.getColorless() < 0)
			needed.setColorless(Math.abs(compare.getColorless()));
		return needed;
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public int getWhite() {
		return white;
	}

	public void setWhite(int white) {
		this.white = white;
	}

	public int getBlack() {
		return black;
	}

	public void setBlack(int black) {
		this.black = black;
	}

	public int getColorless() {
		return colorless;
	}

	public void setColorless(int colorless) {
		this.colorless = colorless;
	}

	public int getAny() {
		return any;
	}

	public void setAny(int any) {
		this.any = any;
	}

	public int compareTo(Mana o) {
		return this.count() - o.count();
	}

	/**
	 *
	 * @param netMana
	 * @return true if this contains any values that mana has
	 */
	public boolean contains(Mana mana) {
		if (mana.black > 0 && this.black > 0)
			return true;
		if (mana.blue > 0 && this.blue > 0)
			return true;
		if (mana.red > 0 && this.red > 0)
			return true;
		if (mana.white > 0 && this.white > 0)
			return true;
		if (mana.green > 0 && this.green > 0)
			return true;
		if (mana.colorless > 0 && this.count() > 0)
			return true;
		
		return false;
	}

}

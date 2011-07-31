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
import mage.util.Copyable;

public class ObjectColor implements Serializable, Copyable<ObjectColor>, Comparable<ObjectColor> {

	public static final ObjectColor WHITE = new ObjectColor("W");
	public static final ObjectColor BLUE = new ObjectColor("U");
	public static final ObjectColor BLACK = new ObjectColor("B");
	public static final ObjectColor RED = new ObjectColor("R");
	public static final ObjectColor GREEN = new ObjectColor("G");
	
	private boolean white;
	private boolean blue;
	private boolean black;
	private boolean red;
	private boolean green;

	public ObjectColor() {}
	
	public ObjectColor(String color) {
		for (int i = 0; i < color.length(); i++) {
			switch (color.charAt(i)) {
			case 'W':
				white = true;
				break;
			case 'U':
				blue = true;
				break;
			case 'B':
				black = true;
				break;
			case 'R':
				red = true;
				break;
			case 'G':
				green = true;
				break;
			}
		}
	}

	public ObjectColor(ObjectColor color) {
		white = color.white;
		blue = color.blue;
		black = color.black;
		red = color.red;
		green = color.green;
	}

	public int getColorCount() {
		int count = 0;
		if (white) count++;
		if (blue) count++;
		if (black) count++;
		if (green) count++;
		if (red) count++;
		return count;
	}

	public void setColor(ObjectColor color) {
		this.setBlack(color.isBlack());
		this.setBlue(color.isBlue());
		this.setGreen(color.isGreen());
		this.setRed(color.isRed());
		this.setWhite(color.isWhite());
	}

	public boolean isColorless() {
		return !(hasColor());
	}
	
	public boolean hasColor() {
		return white | blue | black | red | green;
	}

	public boolean isMulticolored() {
		if (isColorless())
			return false;
		if (white && (blue | black | red | green))
			return true;
		if (blue && (black | red | green))
			return true;
		if (black && (red | green))
			return true;
		if (red && green)
			return true;
		return false;
	}
	
	public boolean isWhite() {
		return white;
	}
	public void setWhite(boolean white) {
		this.white = white;
	}
	public boolean isBlue() {
		return blue;
	}
	public void setBlue(boolean blue) {
		this.blue = blue;
	}
	public boolean isBlack() {
		return black;
	}
	public void setBlack(boolean black) {
		this.black = black;
	}
	public boolean isRed() {
		return red;
	}
	public void setRed(boolean red) {
		this.red = red;
	}
	public boolean isGreen() {
		return green;
	}
	public void setGreen(boolean green) {
		this.green = green;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (white) 
			sb.append("W");
		if (blue) 
			sb.append("U");
		if (black) 
			sb.append("B");
		if (red) 
			sb.append("R");
		if (green) 
			sb.append("G");
		return sb.toString();
	}

	public String getDescription() {
		StringBuilder sb = new StringBuilder();
		if (getColorCount() > 1) {
			return "multicolored";
		} else {
		if (white)
			return "white";
		if (blue)
			return "blue";
		if (black)
			return "black";
		if (red)
			return "red";
		if (green)
			return "green";
		}
		return "colorless";
	}
	
	@Override
	public boolean equals(Object color) {
		if (this == color) 
			return true;
		if (!(color instanceof ObjectColor)) 
			return false;
		ObjectColor test = (ObjectColor) color;
		if (test.white != this.white)
			return false;
		if (test.blue != this.blue)
			return false;
		if (test.black != this.black)
			return false;
		if (test.red != this.red)
			return false;
		if (test.green != this.green)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 23 * hash + (this.white ? 1 : 0);
		hash = 23 * hash + (this.blue ? 1 : 0);
		hash = 23 * hash + (this.black ? 1 : 0);
		hash = 23 * hash + (this.red ? 1 : 0);
		hash = 23 * hash + (this.green ? 1 : 0);
		return hash;
	}

	public boolean contains(ObjectColor color) {
		if (this == color)
			return true;
		if (color.white & this.white)
			return true;
		if (color.blue & this.blue)
			return true;
		if (color.black & this.black)
			return true;
		if (color.red & this.red)
			return true;
		if (color.green & this.green)
			return true;
		return false;
	}

	public boolean shares(ObjectColor color) {
		if (this == color)
			return true;
		if (!hasColor() && !color.hasColor())
			return true;
		return color.white && white || color.blue && blue || color.black && black ||
				color.red && red || color.green && green;
	}

	@Override
	public ObjectColor copy() {
		return new ObjectColor(this);
	}

	public static void main(String[] args) {
		System.out.println(new ObjectColor().shares(new ObjectColor()));
		System.out.println(new ObjectColor("W").shares(new ObjectColor("W")));
		System.out.println(new ObjectColor("U").shares(new ObjectColor("U")));
		System.out.println(new ObjectColor("B").shares(new ObjectColor("B")));
		System.out.println(new ObjectColor("R").shares(new ObjectColor("R")));
		System.out.println(new ObjectColor("G").shares(new ObjectColor("G")));
		System.out.println(new ObjectColor("RG").shares(new ObjectColor("GW")));
		System.out.println(new ObjectColor("BRGUW").shares(new ObjectColor("U")));
		System.out.println(new ObjectColor("{2}").shares(new ObjectColor("{1}")));
		System.out.println(new ObjectColor("WRB").shares(new ObjectColor("UGR")));
		System.out.println("should be false:");
		System.out.println(new ObjectColor("RG").shares(new ObjectColor("WU")));
		System.out.println(new ObjectColor("WRB").shares(new ObjectColor("UG")));
		System.out.println(new ObjectColor("U").shares(new ObjectColor("R")));
		System.out.println(new ObjectColor("G").shares(new ObjectColor("B")));
		System.out.println(new ObjectColor("W").shares(new ObjectColor("1")));

	}

	@Override
	public int compareTo(ObjectColor o) {
		int o1 = 0;
		int o2 = 0;

		if (this.isMulticolored())
			o1 = 6;
		else if(this.isColorless())
			o1 = 0;
		else if(this.isBlack())
			o1 = 1;
		else if(this.isBlue())
			o1 = 2;
		else if(this.isGreen())
			o1 = 3;
		else if(this.isRed())
			o1 = 4;
		else if(this.isWhite())
			o1 = 5;
		if (o.isMulticolored())
			o2 = 6;
		else if(o.isColorless())
			o2 = 0;
		else if(o.isBlack())
			o2 = 1;
		else if(o.isBlue())
			o2 = 2;
		else if(o.isGreen())
			o2 = 3;
		else if(o.isRed())
			o2 = 4;
		else if(o.isWhite())
			o2 = 5;
		return o1 - o2;
	}
	
}

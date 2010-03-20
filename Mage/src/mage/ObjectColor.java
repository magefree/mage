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

public class ObjectColor implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
			case 'U':
				blue = true;
			case 'B':
				black = true;
			case 'R':
				red = true;
			case 'G':
				green = true;
			}
		}
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
	
}

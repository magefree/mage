/*
* Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
import java.util.UUID;
import mage.ConditionalMana;
import mage.Constants.ManaType;
import mage.Mana;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ManaPoolItem implements Serializable {
    
	private int red = 0;
	private int green = 0;
	private int blue = 0;
	private int white = 0;
	private int black = 0;
	private int colorless = 0;
    private ConditionalMana conditionalMana;
    private UUID sourceId;
    
    public ManaPoolItem() {}
    
    public ManaPoolItem(int red, int green, int blue, int white, int black, int colorless, UUID sourceId) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.white = white;
        this.black = black;
        this.colorless = colorless;
        this.sourceId = sourceId;
    }
    
    public ManaPoolItem(ConditionalMana conditionalMana, UUID sourceId) {
        this.conditionalMana = conditionalMana;
        this.sourceId = sourceId;
    }
    
    public ManaPoolItem(final ManaPoolItem item) {
		this.red = item.red;
		this.green = item.green;
		this.blue = item.blue;
		this.white = item.white;
		this.black = item.black;
		this.colorless = item.colorless;
        if (item.conditionalMana != null)
            this.conditionalMana = item.conditionalMana.copy();
        this.sourceId = item.sourceId;
    }
    
    public ManaPoolItem copy() {
        return new ManaPoolItem(this);
    }
    
    public UUID getSourceId() {
        return sourceId;
    }
    
    public int getRed() {
        return red;
    }

    public void removeRed() {
        if (red > 0)
            red--;
    }

    public int getGreen() {
        return green;
    }

    public void removeGreen() {
        if (green > 0)
            green--;
    }

    public int getBlue() {
        return blue;
    }

    public void removeBlue() {
        if (blue > 0)
            blue--;
    }

    public int getBlack() {
        return black;
    }

    public void removeBlack() {
        if (black > 0)
            black--;
    }

    public int getWhite() {
        return white;
    }

    public void removeWhite() {
        if (white > 0)
            white--;
    }

    public int getColorless() {
        return colorless;
    }

    public void removeColorless() {
        if (colorless > 0)
            colorless--;
    }

    public boolean isConditional() {
        return conditionalMana != null;
    }
    
    public ConditionalMana getConditionalMana() {
        return conditionalMana;
    }

    public Mana getMana() {
        return new Mana(red, green, blue, white, black, colorless, 0);
    }

    public int count() {
        if (conditionalMana == null)
            return red + green + blue + white + black + colorless;
        return conditionalMana.count();
    }

    public int get(ManaType manaType) {
        switch(manaType) {
            case BLACK:
                return black;
            case BLUE:
                return blue;
            case GREEN:
                return green;
            case RED:
                return red;
            case WHITE:
                return white;
            case COLORLESS:
                return colorless;
        }
        return 0;
    }

    public void remove(ManaType manaType) {
        switch(manaType) {
            case BLACK:
                if (black > 0)
                    black--;
                break;
            case BLUE:
                if (blue > 0)
                    blue--;
                break;
            case GREEN:
                if (green > 0)
                    green--;
                break;
            case RED:
                if (red > 0)
                    red--;
                break;
            case WHITE:
                if (white > 0)
                    white--;
                break;
            case COLORLESS:
                if (colorless > 0)
                    colorless--;
                break;
        }
    }
}

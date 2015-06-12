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
import mage.Mana;
import mage.constants.Duration;
import mage.constants.ManaType;

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
    private UUID originalId; // originalId of the mana producing ability
    private boolean flag = false;
    private Duration duration;
    private int stock; // amount the item had at the start of casting something

    public ManaPoolItem() {}

    public ManaPoolItem(int red, int green, int blue, int white, int black, int colorless, UUID sourceId, UUID originalId, boolean flag) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.white = white;
        this.black = black;
        this.colorless = colorless;
        this.sourceId = sourceId;
        this.originalId = originalId;
        this.flag = flag;
        this.duration = Duration.EndOfStep;
    }

    public ManaPoolItem(ConditionalMana conditionalMana, UUID sourceId, UUID originalId) {
        this.conditionalMana = conditionalMana;
        this.sourceId = sourceId;
        this.originalId = originalId;
        this.conditionalMana.setManaProducerId(sourceId);
        this.conditionalMana.setManaProducerOriginalId(originalId);
        this.flag = conditionalMana.getFlag();
        this.duration = Duration.EndOfStep;        
    }

    public ManaPoolItem(final ManaPoolItem item) {
        this.red = item.red;
        this.green = item.green;
        this.blue = item.blue;
        this.white = item.white;
        this.black = item.black;
        this.colorless = item.colorless;
        if (item.conditionalMana != null) {
            this.conditionalMana = item.conditionalMana.copy();
        }
        this.sourceId = item.sourceId;
        this.originalId = item.originalId;
        this.flag = item.flag;
        this.duration = item.duration;
        this.stock = item.stock;
    }

    public ManaPoolItem copy() {
        return new ManaPoolItem(this);
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public UUID getOriginalId() {
        return originalId;
    }

    public boolean getFlag() {
        return flag;
    }

    public int getRed() {
        return red;
    }

    public void removeRed() {
        if (red > 0) {
            red--;
        }
    }

    public int getGreen() {
        return green;
    }

    public void removeGreen() {
        if (green > 0) {
            green--;
        }
    }

    public int getBlue() {
        return blue;
    }

    public void removeBlue() {
        if (blue > 0) {
            blue--;
        }
    }

    public int getBlack() {
        return black;
    }

    public void removeBlack() {
        if (black > 0) {
            black--;
        }
    }

    public int getWhite() {
        return white;
    }

    public void removeWhite() {
        if (white > 0) {
            white--;
        }
    }

    public int getColorless() {
        return colorless;
    }

    public void removeColorless() {
        if (colorless > 0) {
            colorless--;
        }
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
        if (conditionalMana == null) {
            return red + green + blue + white + black + colorless;
        }
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

    public void removeAny() {
        int oldCount = count();
        if (black > 0) {
            black--;            
        } else if (blue > 0) {
            blue--;
        } else if (green > 0) {
            green--;
        } else if (red > 0) {
            red--;
        } else if (white > 0) {
            white--;
        } else if (colorless > 0) {
            colorless--;
        }       
        if (stock == oldCount && oldCount > count()) {
            stock--;
        }         
    }
    
    public void remove(ManaType manaType) {
        int oldCount = count();
        switch(manaType) {
            case BLACK:
                if (black > 0) {
                    black--;
                }
                break;
            case BLUE:
                if (blue > 0) {
                    blue--;
                }
                break;
            case GREEN:
                if (green > 0) {
                    green--;
                }
                break;
            case RED:
                if (red > 0) {
                    red--;
                }
                break;
            case WHITE:
                if (white > 0) {
                    white--;
                }
                break;
            case COLORLESS:
                if (colorless > 0) {
                    colorless--;
                }
                break;
        }
        if (stock == oldCount && oldCount > count()) {
            stock--;
        }                
    }
    
    public void clear(ManaType manaType) {
       switch(manaType) {
            case BLACK:
                black = 0;
                break;
            case BLUE:
                blue = 0;
                break;
            case GREEN:
                green = 0;
                break;
            case RED:
                red = 0;
                break;
            case WHITE:
                white = 0;
                break;
            case COLORLESS:
                colorless = 0;
                break;
        }        
    }
    
    public void add(ManaType manaType, int amount) {
       switch(manaType) {
            case BLACK:
                black += amount;
                break;
            case BLUE:
                blue += amount;;
                break;
            case GREEN:
                green += amount;;
                break;
            case RED:
                red  += amount;;
                break;
            case WHITE:
                white += amount;;
                break;
            case COLORLESS:
                colorless += amount;;
                break;
        }        
    }    

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
        
}
